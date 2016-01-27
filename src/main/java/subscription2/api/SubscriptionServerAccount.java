package subscription2.api;

import subscription2.exceptions.SubscriptionAlreadyExistsException;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ofirp
 * Date: 1/24/2016
 * Time: 2:25 PM
 */
public interface SubscriptionServerAccount<O> {

    String onSubscribe(O inSubscribeRequest, String accountId, Map<String, Object> params) throws SubscriptionAlreadyExistsException;

    void onUnSubscribe(O inUnSubscribeRequest, String accountId);

    void onUpdateSubscribe(O updateSubscribeRequest, String accountId, Map<String, Object> params);

    void onEvent(Object event, String accountId, Map<String, Object> params);
}
