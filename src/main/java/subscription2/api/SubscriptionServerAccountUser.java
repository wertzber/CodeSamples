package subscription2.api;

import subscription2.exceptions.SubscriptionAlreadyExistsException;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ofirp
 * Date: 1/24/2016
 * Time: 3:00 PM
 */
public interface SubscriptionServerAccountUser<O> {
    String onSubscribe(O inSubscribeRequest, String accountId, String userId, Map<String, Object> params) throws SubscriptionAlreadyExistsException;

    void onUpdateSubscribe(O updateSubscribeRequest, String accountId, String userId, Map<String, Object> params);

    void onUnSubscribe(O inUnSubscribeRequest, String account, String userId, Map<String, Object> params);

    void onEvent(Object event, String accountId, String userId, Map<String, Object> params);
}
