package subscription.api;

import com.liveperson.api.ams.aam.SubscribeExConversations;
import com.liveperson.api.server.Remote;
import com.liveperson.api.server.RequestMsg;
import com.liveperson.api.websocket.WsRequestMsg;

import java.util.Map;

/**
 * Created by eladw on 1/4/2016.
 * Server subscription API
 * O - original subscribe request
 */
//TODO - naming
public interface SubscriptionServer<O> {

    public void onSubscribe(O inSubscribeRequest, String accountId, String userId, Map<String, String> params );
    public void onUnSubscribe(O inUnSubscribeRequest,String account, String userId, Map<String, String> params);
    public void onUpdateSubscribe(O updateSubscribeRequest, String account, String userId, Map<String, String> params);

}
