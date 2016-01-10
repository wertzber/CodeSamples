package subscription.impl;

import com.liveperson.api.ams.aam.SubscribeExConversations;
import com.liveperson.api.server.RequestMsg;
import com.liveperson.api.websocket.WsRequestMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.*;
import subscription.data.aam.AamPredicate;
import subscription.data.aam.ExtendedConversation;

import java.util.List;
import java.util.Map;

/**
 * Created by eladw on 1/4/2016.
 * Implement specific AAM sunsription server
 */
public class SubscriptionServerAamImpl<P> implements
        SubscriptionServer<RequestMsg<SubscribeExConversations>> {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionServerAamImpl.class);

    public static final String SUBSCRIBE_ID = "SubscribeId";
    public static final String ACCOUNT = "account";

    SubscriptionActions<AamPredicate, SubscribeExConversations> aamSubscriptionActions = new SubscriberActionsImpl
            <ExtendedConversation, SubscribeExConversations, AamPredicate> (AamPredicate.class);

    SubscriptionFilterManager<SubscriptionFilter> aamFilterManager;
           // new SubscriptionFilterManagerImpl<SubscriptionFilter>();
    private SubscriptionSender sender;

    public SubscriptionServerAamImpl(SubscriptionFilterManager<SubscriptionFilter> subscriptionFilterManager,
                                     SubscriptionSender sender){
        this.sender = sender;
        this.aamFilterManager = subscriptionFilterManager;

    }

    @Override
    public void onSubscribe(WsRequestMsg inSubscribeRequest,String accountId, String userId,
                            Map<String, String> params) {
        SubscribeExConversations inSubscribeBody = (SubscribeExConversations) inSubscribeRequest.body;
        boolean filterRes = aamFilterManager.testFilters(SubscriptionFilterManager.FilterType.IN, inSubscribeBody);
        if(filterRes){
            String subsId = aamSubscriptionActions.addSubscriber(
                    accountId, userId, inSubscribeBody);
            try {
                sender.send("onSubscribe success, subs id:" + subsId);
            } catch (InterruptedException e) {
                logger.error("Failed to send update of id: " + subsId, e);
            }
        } else {
            logger.warn("subscription failed " + inSubscribeBody);
        }
    }

    @Override
    public void onUnSubscribe(RequestMsg<SubscribeExConversations> inUnSubscribeRequest, final String account, String userId, Map<String, String> params) {
        if(userId!=null || account==null){
            List<String> userSubscriptions = aamSubscriptionActions.getUserSubscriptions(account, userId);
            userSubscriptions.stream().map(id->aamSubscriptionActions.removeSubscriber(account,id));
            userSubscriptions.remove(userId);
        } else {
            logger.warn("Failed to do unsubscribe, wrong input: userId:" + userId + " account:" + account);
        }
    }

    @Override
    public void onUpdateSubscribe(RequestMsg<SubscribeExConversations> updateSubscribeRequest, final String account, String userId,
                                  Map<String, String> params) {

    }
}
