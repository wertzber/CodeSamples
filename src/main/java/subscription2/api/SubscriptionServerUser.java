package subscription2.api;

import subscription2.exceptions.SubscriptionAlreadyExistsException;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ofirp
 * Date: 1/24/2016
 * Time: 2:57 PM
 */
public interface SubscriptionServerUser<O> {

    String onSubscribe(O inSubscribeRequest, String userId, Map<String, Object> params) throws SubscriptionAlreadyExistsException;

    void onUnSubscribe(O inUnSubscribeRequest, String userId);

    void onUpdateSubscribe(O updateSubscribeRequest, String userId, Map<String, Object> params);

    void onEvent(Object event, String userId, Map<String, Object> params);
}
