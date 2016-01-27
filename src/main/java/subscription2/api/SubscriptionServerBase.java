package subscription2.api;

import subscription.api.SubscriptionConverter;
import subscription.api.SubscriptionResultModifier;
import subscription2.exceptions.SubscriptionAlreadyExistsException;

import java.util.Map;
import java.util.function.Function;

/**
 * Created by eladw and ofirp
 * @param <O> Original subscription msg type (e.g. SubscribeExConversation)
 * @param <P> The type for the predicate (e.g. ExtendedConversation)
 */
public interface SubscriptionServerBase<O,P> {

    /**
     * Registers a new subscription
     * @param inSubscribeRequest the subscribe request message
     * @param params Optional parameters
     * @return the subscription id
     * @throws SubscriptionAlreadyExistsException in case subscription already exists
     */
    String onSubscribe(O inSubscribeRequest, Map<String, Object> params) throws SubscriptionAlreadyExistsException;

    void onUnSubscribe(O inUnSubscribeRequest, String subscribeId);

    void onUpdateSubscribe(O updateSubscribeRequest, String subscribeId, Map<String, Object> params);

    <F> void registerIncomingEventFilter(Class<F> filteredClass, Function<F, F> incomingEventFilter);

    <C> void registerIncomingEventConverter(Class<C> convertedClass, SubscriptionConverter<C,P> incomingEventConverter);

    void registerResultModifier(SubscriptionResultModifier<P,P> resultModifier);

    <C> void registerOutgoingEventConverter(SubscriptionConverter<P,C> outgoingEventConverter);

    void onEvent(Object event, Map<String, Object> params);
}
