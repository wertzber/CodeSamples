package subscription.impl;

import com.liveperson.api.ams.aam.ExConversationChangeNotification;
import com.liveperson.api.ams.aam.SubscribeExConversations;
import com.liveperson.api.ams.aam.types.ExtendedConversationDetails;
import com.liveperson.api.ams.aam.types.ExtendedConversationDetailsBuilder;
import com.liveperson.api.ams.cm.types.ConversationDetailsBuilder;
import com.liveperson.api.websocket.WsRequestMsg;
import com.liveperson.messaging.async.types.cm.entities.Conversation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.*;
import subscription.data.aam.AamPredicate;
import subscription.data.aam.ExtendedConversation;
import subscription.data.subscribe.SubscriptionData;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by eladw on 1/4/2016.
 * Implement specific AAM sunsription server
 */
public class SubscriptionServerAamImpl extends SubscriptionServerBaseImpl<WsRequestMsg, ExtendedConversation, Predicate> {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionServerAamImpl.class);

    private Predicate aamInFilter;
    private List<SubscriptionResultModifier> aamResultModifiers;
    private SubscriptionConverter<Conversation, ExtendedConversation> incomingEventsConverter;
    private SubscriptionConverter<ExtendedConversationDetails, ExConversationChangeNotification> outgoingEventsConverter;
    private SubscriptionSender sender;

    public SubscriptionServerAamImpl(Predicate aamInFilter,
                                     List<SubscriptionResultModifier> aamResultModifiers,
                                     SubscriptionConverter<Conversation, ExtendedConversation> incomingEventsConverter,
                                     SubscriptionConverter<ExtendedConversationDetails,ExConversationChangeNotification> outgoingEventsConverter,
                                     SubscriptionSender sender){
        super();
        this.aamInFilter = aamInFilter;
        this.aamResultModifiers = aamResultModifiers;
        this.incomingEventsConverter = incomingEventsConverter;
        this.outgoingEventsConverter = outgoingEventsConverter;
        this.sender = sender;
    }

    public SubscriptionServerAamImpl() {
        super();
    }

    @Override
    public String onSubscribe(WsRequestMsg inSubscribeRequest, Map<String, Object> params) {
        String subscribeId = super.onSubscribe(inSubscribeRequest, params);
        onSubsribeSender(subscribeId);
        return subscribeId;
    }



    @Override
    public String onAccountSubscribe(WsRequestMsg inSubscribeRequest, String accountId, Map<String, Object> params) {
        String subscribeId = super.onAccountSubscribe(inSubscribeRequest, accountId, params);
        onSubsribeSender(subscribeId);
        return subscribeId;
    }

    @Override
    public String onUserSubscribe(WsRequestMsg inSubscribeRequest, String userId, Map<String, Object> params) {
        String subscribeId = super.onUserSubscribe(inSubscribeRequest, userId, params);
        onSubsribeSender(subscribeId);
        return subscribeId;
    }

    @Override
    public String onAccountAndUserSubscribe(WsRequestMsg inSubscribeRequest, String accountId, String userId, Map<String, Object> params) {
        String subscribeId = super.onAccountAndUserSubscribe(inSubscribeRequest, accountId, userId, params);
        onSubsribeSender(subscribeId);
        return subscribeId;
    }

    @Override
    public void onUnSubscribe(WsRequestMsg inUnSubscribeRequest, String subscribeId) {
        super.onUnSubscribe(inUnSubscribeRequest, subscribeId);
    }

    @Override
    public void onAccountUnSubscribe(WsRequestMsg inUnSubscribeRequest, String account) {
        super.onAccountUnSubscribe(inUnSubscribeRequest, account);
    }

    @Override
    public void onUserUnSubscribe(WsRequestMsg inUnSubscribeRequest, String userId) {
        super.onUserUnSubscribe(inUnSubscribeRequest, userId);
    }

    @Override
    public void onAccountAndUserUnSubscribe(WsRequestMsg inUnSubscribeRequest, String account, String userId, Map<String, Object> params) {
        super.onAccountAndUserUnSubscribe(inUnSubscribeRequest, account, userId, params);
    }

    @Override
    public void onUpdateSubscribe(WsRequestMsg updateSubscribeRequest, String subscribeId, Map<String, Object> params) {
        super.onUpdateSubscribe(updateSubscribeRequest, subscribeId, params);
    }

    @Override
    public void onEvent(Object event, Map<String, Object> params) {
        super.onEvent(event, params);
    }

    @Override
    public void onUserEvent(Object event, String userId, Map<String, Object> params) {
        super.onUserEvent(event, userId, params);
    }

    @Override
    public void onAccountAndUserEvent(Object event, String accountId, String userId, Map<String, Object> params) {
        super.onAccountAndUserEvent(event, accountId, userId, params);
    }

    @Override
    public Set<String> getAccountSubscriptionsIds(String account) {
        return super.getAccountSubscriptionsIds(account);
    }

    @Override
    public SubscriptionData<Predicate, WsRequestMsg> getAccountSingleSubscription(String account, String subscribeId) {
        return super.getAccountSingleSubscription(account, subscribeId);
    }

    @Override
    public Set<String> getUserSubscriptions(String userId) {
        return super.getUserSubscriptions(userId);
    }

    @Override
    public SubscriptionData<Predicate, WsRequestMsg> getUserSingleSubscription(String userId, String subscribeId) {
        return super.getUserSingleSubscription(userId, subscribeId);
    }

    private void onSubsribeSender(String subscribeId) {
        try {
            sender.send("onSubscribe success, subs id:" + subscribeId);
        } catch (InterruptedException e) {
            logger.error("Failed to send update of id: " + subscribeId, e);
        }
    }

    private void onEventFlow(Object event, Map<String, Object> params){
        ExtendedConversation exConv = null;

        //filter in

        //convertFormat
        if(event instanceof Conversation){
            exConv = this.incomingEventsConverter.convert((Conversation) event);
        } else {
            logger.error("Unsupported incoming event " + event.getClass());
        }



        final ExtendedConversationDetails finalExConvDetails = new ExtendedConversationDetailsBuilder()
                .withConvId(exConv.getAamConversation().convId)
                .withConversationDetails(new ConversationDetailsBuilder()
                        .withConvId(exConv.getAamConversation().convId)
                        .withBrandId(exConv.getAamConversation().brandId)
                        .withNote("convert out")
                        .withState(exConv.getAamConversation().state)
                        .build())
                .build();

        //Run modifiers
        for(SubscriptionResultModifier rsModify : aamResultModifiers){
            rsModify.modify(finalExConvDetails, params);
        }

        final ExtendedConversation finalExConv = exConv;
        List<ExConversationChangeNotification> notifications = accountSubscriptions.entrySet().stream()
                .filter(val -> {
                    return val.getValue().getSubscribePredicate().test(finalExConv);
                })
                //convert out
                .map(key -> this.outgoingEventsConverter.convert(key.getValue().getSubscriptionId(), finalExConvDetails))
                .collect(Collectors.toList());

        //send
        try {
            sender.send(notifications);
        } catch (InterruptedException e) {
            logger.info("failed to send msg ", e);
        }

    }
//    @Override
//    public String onSubscribe(WsRequestMsg inSubscribeRequest,String accountId, String userId,
//                            Map<String, Object> params) {
//        SubscribeExConversations inSubscribeBody = (SubscribeExConversations) inSubscribeRequest.body;
//        String subsId = onSubscribe(inSubscribeRequest, accountId, userId, params);
//        try {
//            sender.send("onSubscribe success, subs id:" + subsId);
//        } catch (InterruptedException e) {
//            logger.error("Failed to send update of id: " + subsId, e);
//        }
//
//    }
//
//    @Override
//    public void onUnSubscribe(WsRequestMsg inUnSubscribeRequest, final String account, String userId, Map<String, Object> params) {
//        if(userId!=null || account==null){
//            aamSubscriptionActions.removeSubscriberUser(account, userId);
//        } else {
//            logger.warn("Failed to do unsubscribe, wrong input: userId:" + userId + " account:" + account);
//        }
//    }
//
//    @Override
//    public void onUpdateSubscribe(WsRequestMsg updateSubscribeRequest, final String account, String userId,
//                                  Map<String, Object> params) {
//    }
//
//    @Override
//    public void onEvent(Object event, String account, String userId, Map<String, Object> params ) {
//        ExtendedConversation exConv = null;
//
//
//        //convertFormat
//        if(event instanceof Conversation){
//            exConv = this.incomingEventsConverter.convert((Conversation) event);
//        } else {
//            logger.error("Unsupported incoming event " + event.getClass());
//        }
//
//
//
//        final ExtendedConversationDetails finalExConvDetails = new ExtendedConversationDetailsBuilder()
//                .withConvId(exConv.getAamConversation().convId)
//                .withConversationDetails(new ConversationDetailsBuilder()
//                        .withConvId(exConv.getAamConversation().convId)
//                        .withBrandId(exConv.getAamConversation().brandId)
//                        .withNote("convert out")
//                        .withState(exConv.getAamConversation().state)
//                        .build())
//                .build();
//
//        //Run modifiers
//        for(SubscriptionResultModifier rsModify : aamResultModifiers){
//            rsModify.modify(finalExConvDetails, params);
//        }
//
//        final ExtendedConversation finalExConv = exConv;
//        List<ExConversationChangeNotification> notifications = accountSubscriptions.entrySet().stream()
//                .filter(val -> {
//                    return val.getValue().getSubscribePredicate().test(finalExConv);
//                })
//                //convert out
//                .map(key -> this.outgoingEventsConverter.convert(key.getValue().getSubscriptionId(), finalExConvDetails))
//                .collect(Collectors.toList());
//
//        //send
//        try {
//            sender.send(notifications);
//        } catch (InterruptedException e) {
//            logger.info("failed to send msg ", e);
//        }
//
//    }



    public SubscriptionSender getSender() {
        return sender;
    }
}
