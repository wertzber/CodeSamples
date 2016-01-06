package subscription.api;

import com.liveperson.api.ams.aam.SubscribeExConversations;
import com.liveperson.api.server.Remote;
import com.liveperson.api.server.RequestMsg;

import java.util.Map;

/**
 * Created by eladw on 1/4/2016.
 * Server subscription API
 * O - original subscribe request
 */
//TODO - naming
public interface SubscriptionServer<O> {

    public void onSubscribe(Remote remote,O inSubscribeRequest, Map<String, String> params );
    public void onUnSubscribe(Remote remote, O inUnSubscribeRequest, Map<String, String> params);
    public void onUpdateSubscribe(Remote remote, O updateSubscribeRequest, Map<String, String> params);

}
