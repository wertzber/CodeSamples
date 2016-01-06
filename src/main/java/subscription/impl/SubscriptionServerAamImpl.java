package subscription.impl;

import com.liveperson.api.ams.aam.SubscribeExConversations;
import com.liveperson.api.server.Remote;
import com.liveperson.api.server.RequestMsg;
import subscription.api.SubscriptionServer;
import subscription.api.SubscriberActions;
import subscription.data.aam.ExtendedConversation;
import subscription.utils.SubscriberUtils;

import java.util.Map;

/**
 * Created by eladw on 1/4/2016.
 */
public class SubscriptionServerAamImpl implements
        SubscriptionServer<RequestMsg<SubscribeExConversations>> {

    public static final String SUBSCRIBE_ID = "SubscribeId";
    public static final String ACCOUNT = "account";

    SubscriberActions<ExtendedConversation, SubscribeExConversations> aamSubscriberActions = new SubscriberActionsImpl<>();

    SubscriberSenderImpl sender = new SubscriberSenderImpl();

    @Override
    public void onSubscribe(Remote remote, RequestMsg<SubscribeExConversations> inSubscribeRequest, Map<String, String> params) {
        SubscribeExConversations inSubscribeBody = inSubscribeRequest.getBody();
        final String subscribeId = SubscriberUtils.generateSubscriberId();
        params.compute(SUBSCRIBE_ID, (k,v)-> subscribeId);
        aamSubscriberActions.addSubscriber(
                inSubscribeBody.brandId, params.get("userId"), inSubscribeBody);
    }

    @Override
    public void onUnSubscribe(Remote remote, RequestMsg<SubscribeExConversations> inUnSubscribeRequest, Map<String, String> params) {
        String subscribeId = params.get(SUBSCRIBE_ID);
        //clean subscribers
        aamSubscriberActions.removeSubscriber(params.get(ACCOUNT), subscribeId);
    }

    @Override
    public void onUpdateSubscribe(Remote remote, RequestMsg<SubscribeExConversations> updateSubscribeRequest,
                                  Map<String, String> params) {

    }
}
