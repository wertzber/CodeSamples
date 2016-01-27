package subscription2.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.SubscriptionConverter;
import subscription.api.SubscriptionResultModifier;
import subscription.api.SubscriptionSender;
import subscription.data.aam.ExtendedConversation;
import subscription2.api.SubscriptionServerBase;
import subscription.data.subscribe.SubscriptionData;
import subscription2.exceptions.SubscriptionAlreadyExistsException;
import subscription2.utils.SubscriptionServerUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by eladw and ofirp
 * @param <O> Original subscription msg type (e.g. SubscribeExConversation)
 * @param <P> The type for the predicate (e.g. ExtendedConversation)
 */
public class SubscriptionServerBaseImpl<O,P> implements SubscriptionServerBase<O,P> {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionServerBaseImpl.class);

    final protected Map<String,SubscriptionData<P,O>> subscriptionsMap = new ConcurrentHashMap<>(50);

    // REQUIRED FIELDS
    private Predicate<P> queryPredicate;
    private SubscriptionSender notificationSender;

    // OPTIONAL FIELDS
    private Map<Class<?>, Function> incomingEventFiltersMap = new ConcurrentHashMap<>(2);
    private Map<Class<?>, SubscriptionConverter> incomingEventConvertersMap = new ConcurrentHashMap<>(2);
    private List<SubscriptionResultModifier<P,P>> resultModifiers = new ArrayList<>(0);
    private SubscriptionConverter outgoingEventConverter = SubscriptionServerUtils.getDefaultSubscriptionConverter();

    public SubscriptionServerBaseImpl(Predicate<P> queryPredicate, SubscriptionSender notificationSender) {
        if (queryPredicate == null) throw new NullPointerException("query predicate must be supplied to the Subscription Server");
        if (notificationSender == null) throw new NullPointerException("notification sender must be supplied to the Subscription Server");
        this.queryPredicate = queryPredicate;
        this.notificationSender = notificationSender;
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
    public <C> void registerOutgoingEventConverter(SubscriptionConverter<P,C> outgoingEventConverter) {
        this.outgoingEventConverter = outgoingEventConverter;
    }

    @Override
    public String onSubscribe(O inSubscribeRequest, Map<String, Object> params) throws SubscriptionAlreadyExistsException {
        // TODO convert inSubscribeRequest to JSON before logging
        logger.debug("Handling new subscribe request {}", inSubscribeRequest);
        String subscriptionId = UUID.randomUUID().toString();
        SubscriptionData<P,O> subsData =  new SubscriptionData<>(
                queryPredicate,
                inSubscribeRequest,
                subscriptionId);
        if (subscriptionsMap.putIfAbsent(subscriptionId, subsData) != null) {
            throw new SubscriptionAlreadyExistsException(subscriptionId);
        }
        // TODO convert inSubscribeRequest to JSON before logging
        logger.debug("Added subscription Id with subscription data {}", subscriptionId, subsData);
        return subscriptionId;
    }

    @Override
    public void onUnSubscribe(O inUnSubscribeRequest, String subscribeId) {
        // TODO convert inSubscribeRequest to JSON before logging
        logger.debug("Handling UnSubscribe request {}", inUnSubscribeRequest);
        subscriptionsMap.remove(subscribeId);
        logger.debug("Removed subscription Id {}", subscribeId);
    }

    @Override
    public void onUpdateSubscribe(O updateSubscribeRequest, String subscriptionId, Map<String, Object> params) {
        // TODO convert inSubscribeRequest to JSON before logging
        logger.debug("Handling subscribe update request {}", updateSubscribeRequest);
        SubscriptionData<P,O> subsData =  new SubscriptionData<>(
                queryPredicate,
                updateSubscribeRequest,
                subscriptionId);
        subscriptionsMap.put(subscriptionId, subsData);
        // TODO convert inSubscribeRequest to JSON before logging
        logger.debug("Updated subscription Id {} with subscription data {}", subscriptionId, subsData);
    }

    @Override
    public void onEvent(Object event, Map<String, Object> params) {
        final Object filteredEvent = incomingEventFiltersMap.getOrDefault(event.getClass(), SubscriptionServerUtils.getDefaultEventFilter()).apply(event);
        final P convertedIncomingEvent = (P)incomingEventConvertersMap.getOrDefault(event.getClass(), SubscriptionServerUtils.getDefaultSubscriptionConverter()).convert(filteredEvent);
        final P predicateResult = null; // TODO execute predicate on all subscriptions
        final P p = executeModifiers(params, predicateResult);


    }

    private P executeModifiers(Map<String, Object> params, final P predicateResult) {
        P accumulatedResult
        for (SubscriptionResultModifier<P,P> resultModifier : resultModifiers) {
            predicateResult = resultModifier.modify(predicateResult, params);
        }
        return predicateResult;
    }

}
