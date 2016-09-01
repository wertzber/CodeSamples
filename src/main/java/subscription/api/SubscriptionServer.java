package subscription.api;

import com.liveperson.api.ams.aam.SubscribeExConversations;
import com.liveperson.api.server.Remote;
import com.liveperson.api.server.RequestMsg;
import com.liveperson.api.websocket.WsRequestMsg;
import subscription.data.subscribe.SubscriptionData;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by eladw on 1/4/2016.
 * Server subscription API
 * O - original subscribe request
 * T - predicate object to use, such as ExConv in case of aam
 * R- Request
 */
//TODO - naming
public interface SubscriptionServer<O,T,R> {

    String onSubscribe(O inSubscribeRequest, Map<String, Object> params);    //return subscribe id
    String onAccountSubscribe(O inSubscribeRequest, String accountId ,Map<String, Object> params );
    String onUserSubscribe(O inSubscribeRequest, String userId ,Map<String, Object> params );
    String onAccountAndUserSubscribe(O inSubscribeRequest, String accountId, String userId, Map<String, Object> params );

    void onUnSubscribe(O inUnSubscribeRequest,String subscribeId);
    void onAccountUnSubscribe(O inUnSubscribeRequest,String account);
    void onUserUnSubscribe(O inUnSubscribeRequest,String userId);
    void onAccountAndUserUnSubscribe(O inUnSubscribeRequest,String account, String userId, Map<String, Object> params);

    void onUpdateSubscribe(O updateSubscribeRequest, String subscribeId, Map<String, Object> params);

    void onEvent(Object event, Map<String, Object> params);
    void onAccountEvent(Object event, String account, Map<String, Object> params);
    void onUserEvent(Object event, String userId,  Map<String, Object> params);
    void onAccountAndUserEvent(Object event, String AccountId, String userId, Map<String, Object> params);

    Set<String> getAccountSubscriptionsIds(String account);
    SubscriptionData<R, O> getAccountSingleSubscription(String account, String subscribeId);
    Set<String> getUserSubscriptions(String userId);
    SubscriptionData<R, O> getUserSingleSubscription(String userId, String subscribeId);



}
