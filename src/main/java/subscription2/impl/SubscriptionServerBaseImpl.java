package subscription2.impl;

import com.liveperson.api.ams.GenericSubscribe;
import com.liveperson.api.server.RequestMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.SubscriptionConverter;
import subscription.api.SubscriptionResultModifier;
import subscription.api.SubscriptionSender;
import subscription2.api.DataSupplier;
import subscription2.api.SubscriptionServerBase;
import subscription.data.subscribe.SubscriptionData;
import subscription2.events.EventInfo;
import subscription2.exceptions.SubscriptionAlreadyExistsException;
import subscription2.predicate.ChangeType;
import subscription2.predicate.ChangeTypeEvaluator;
import subscription2.predicate.PredicateSupplier;
import subscription2.utils.SubscriptionServerUtils;

import javax.ws.rs.core.Response;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by eladw and ofirp
 * @param <O> Original subscription request msg type (e.g. SubscribeExConversation)
 * @param <P> The type for the predicate (e.g. ExtendedConversation)
 */
public class SubscriptionServerBaseImpl<O extends RequestMsg,P> implements SubscriptionServerBase<O,P> {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionServerBaseImpl.class);

    protected final Map<String,SubscriptionData<O,P>> subscriptionsMap = new ConcurrentHashMap<>(50);
    private final SubscriptionServerUtils<P> subscriptionServerUtils = new SubscriptionServerUtils<>();

    // REQUIRED FIELDS
    private SubscriptionSender subscriptionSender;
    private PredicateSupplier<O,P> predicateSupplier;
    private DataSupplier<P> dataSupplier;

    // OPTIONAL FIELDS
    private Map<Class<?>, Function> incomingEventFiltersMap = new ConcurrentHashMap<>(2);
    private Map<Class<?>, SubscriptionConverter> incomingEventConvertersMap = new ConcurrentHashMap<>(2);
    private List<SubscriptionResultModifier<P,P>> resultModifiers = new ArrayList<>(0);
    private SubscriptionConverter outgoingResultConverter = subscriptionServerUtils.getDefaultSubscriptionConverter();

    public SubscriptionServerBaseImpl(SubscriptionSender subscriptionSender, PredicateSupplier<O,P> predicateSupplier, DataSupplier<P> dataSupplier) {
        if (subscriptionSender == null) throw new IllegalArgumentException("subscriptionSender must be supplied to the Subscription Server");
        if (predicateSupplier == null) throw new IllegalArgumentException("predicateSupplier must be supplied to the Subscription Server");
        if (dataSupplier == null) throw new IllegalArgumentException("dataSupplier must be supplied to the Subscription Server");
        this.subscriptionSender = subscriptionSender;
        this.predicateSupplier = predicateSupplier;
        this.dataSupplier = dataSupplier;
    }

    @Override
    public <F> void registerIncomingEventFilter(Class<F> filteredClass, Function<F, F> incomingEventFilter) {
        incomingEventFiltersMap.put(filteredClass, incomingEventFilter);
    }

    @Override
    public <C> void registerIncomingEventConverter(Class<C> convertedClass, SubscriptionConverter<C,P> incomingEventConverter) {
        incomingEventConvertersMap.put(convertedClass, incomingEventConverter);
    }

    @Override
    public void registerResultModifier(SubscriptionResultModifier<P,P> resultModifier) {
        resultModifiers.add(resultModifier);
    }

    @Override
    public <C> void registerOutgoingResultConverter(SubscriptionConverter<P, C> outgoingResultConverter) {
        this.outgoingResultConverter = outgoingResultConverter;
    }

    @Override
    public String onSubscribe(String clientId, O inSubscribeRequest, Map<String, Object> params) throws SubscriptionAlreadyExistsException {
        // TODO convert inSubscribeRequest to JSON before logging
        logger.debug("Handling new subscribe request {}", inSubscribeRequest);
        String subscriptionId = UUID.randomUUID().toString();
        SubscriptionData<O,P> subsData =  new SubscriptionData<>(
                predicateSupplier.get(inSubscribeRequest),
                inSubscribeRequest,
                subscriptionId,
                clientId);
        if (subscriptionsMap.putIfAbsent(subscriptionId, subsData) != null) {
            throw new SubscriptionAlreadyExistsException(subscriptionId);
        }
        // TODO convert inSubscribeRequest to JSON before logging
        logger.debug("Added subscription Id {} with subscription data {}", subscriptionId, subsData);
        subscriptionSender.send(clientId, inSubscribeRequest.response(Response.Status.OK, new GenericSubscribe.Response(subscriptionId)));
        runSubscriptionOnAllData(subsData);
        return subscriptionId;
    }

    private void runSubscriptionOnAllData(SubscriptionData<O, P> subsData) {
        final Collection<P> historyData = dataSupplier.getHistoryData(subsData.getOrigSubscribe().getBody());
        historyData.forEach(dataItem -> executeOutgoingFlow(dataItem, subsData));
    }

    @Override
    public void onUnSubscribe(String clientId, O inUnSubscribeRequest, String subscribeId) {
        // TODO convert inSubscribeRequest to JSON before logging
        logger.debug("Handling UnSubscribe request {}", inUnSubscribeRequest);
        subscriptionsMap.remove(subscribeId);
        logger.debug("Removed subscription Id {}", subscribeId);
    }

    @Override
    public void onUpdateSubscribe(String clientId, O updateSubscribeRequest, String subscriptionId, Map<String, Object> params) {
        // TODO convert inSubscribeRequest to JSON before logging
        logger.debug("Handling subscribe update request {}", updateSubscribeRequest);
        SubscriptionData<O,P> subsData =  new SubscriptionData<>(
                predicateSupplier.get(updateSubscribeRequest),
                updateSubscribeRequest,
                subscriptionId,
                clientId);
        subscriptionsMap.put(subscriptionId, subsData);
        // TODO convert inSubscribeRequest to JSON before logging
        logger.debug("Updated subscription Id {} with subscription data {}", subscriptionId, subsData);
    }

    @Override
    public void onEvent(EventInfo eventInfo) {
        final Object filteredEvent = incomingEventFiltersMap.getOrDefault(eventInfo.getNewState().getClass(), subscriptionServerUtils.getDefaultFilter()).apply(eventInfo.getNewState());
        final P convertedIncomingEvent = (P)incomingEventConvertersMap.getOrDefault(eventInfo.getNewState(), subscriptionServerUtils.getDefaultSubscriptionConverter()).convert(filteredEvent);
        subscriptionsMap.forEach((subscriptionId, subscriptionData) -> executeOutgoingFlow(convertedIncomingEvent, subscriptionData));
    }

    private void executeOutgoingFlow(P convertedIncomingEvent, SubscriptionData<O, P> subscriptionData) {
        final Predicate<P> subscribePredicate = subscriptionData.getSubscribePredicate();
        final ChangeType changeType = ChangeTypeEvaluator.evaluate(subscribePredicate, null, convertedIncomingEvent);
        if (changeType != ChangeType.NO_CHANGE) {
            final P modifiedResult = executeModifiers(convertedIncomingEvent);
            final Object convertedOutgoingResult = outgoingResultConverter.convert(modifiedResult);
            // filtering outgoing messages is implemented by the client
            // until subscribe API incorporates a way for the client to provide its filters
            subscriptionSender.send(subscriptionData.getClientId(), convertedOutgoingResult);
        }
    }

    private P executeModifiers(final P predicateResult) {
        P accumulatedResult = predicateResult;
        for (SubscriptionResultModifier<P,P> resultModifier : resultModifiers) {
            accumulatedResult = resultModifier.modify(predicateResult);
        }
        return accumulatedResult;
    }

}
