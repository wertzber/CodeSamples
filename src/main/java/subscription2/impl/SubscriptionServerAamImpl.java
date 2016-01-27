package subscription2.impl;

import com.liveperson.api.ams.aam.ExConversationChangeNotification;
import com.liveperson.api.ams.aam.types.ExtendedConversationDetails;
import com.liveperson.api.ams.aam.types.ExtendedConversationDetailsBuilder;
import com.liveperson.api.ams.cm.types.ConversationDetailsBuilder;
import com.liveperson.api.websocket.WsRequestMsg;
import com.liveperson.messaging.async.types.cm.entities.Conversation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.SubscriptionConverter;
import subscription.api.SubscriptionResultModifier;
import subscription.api.SubscriptionSender;
import subscription.data.aam.ExtendedConversation;
import subscription2.exceptions.SubscriptionAlreadyExistsException;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by eladw on 1/4/2016.
 * Implement specific AAM subscription server
 */
public class SubscriptionServerAamImpl extends SubscriptionServerAccountUserImpl<WsRequestMsg, ExtendedConversation, Predicate> {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionServerAamImpl.class);

    private Predicate aamInFilter;
    private List<SubscriptionResultModifier<ExtendedConversation, ExtendedConversation>> aamResultModifiers;
    private SubscriptionConverter<Conversation, ExtendedConversation> incomingEventsConverter;
    private SubscriptionConverter<ExtendedConversationDetails, ExConversationChangeNotification> outgoingEventsConverter;
    private SubscriptionSender<ExConversationChangeNotification> sender;

    public SubscriptionServerAamImpl(Predicate aamInFilter,
                                     List<SubscriptionResultModifier<ExtendedConversation, ExtendedConversation>> aamResultModifiers,
                                     SubscriptionConverter<Conversation, ExtendedConversation> incomingEventsConverter,
                                     SubscriptionConverter<ExtendedConversationDetails, ExConversationChangeNotification> outgoingEventsConverter,
                                     SubscriptionSender<ExConversationChangeNotification> sender){
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
    public String onSubscribe(WsRequestMsg inSubscribeRequest, Map<String, Object> params) throws SubscriptionAlreadyExistsException {
        String subscribeId = super.onSubscribe(inSubscribeRequest, params);
        onSubscribeSender(subscribeId);
        return subscribeId;
    }

    @Override
    public String onSubscribe(WsRequestMsg inSubscribeRequest, String accountId, String userId, Map<String, Object> params) throws SubscriptionAlreadyExistsException {
        final String subscribeId = super.onSubscribe(inSubscribeRequest, accountId, userId, params);
        onSubscribeSender(subscribeId);
        return subscribeId;
    }

    @Override
    public void onUnSubscribe(WsRequestMsg inUnSubscribeRequest, String subscribeId) {
        super.onUnSubscribe(inUnSubscribeRequest, subscribeId);
    }

    @Override
    public void onUnSubscribe(WsRequestMsg inUnSubscribeRequest, String accountId, String userId, Map<String, Object> params) {
        super.onUnSubscribe(inUnSubscribeRequest, accountId, userId, params);
    }

    @Override
    public void onUpdateSubscribe(WsRequestMsg updateSubscribeRequest, String subscriptionId, Map<String, Object> params) {
        super.onUpdateSubscribe(updateSubscribeRequest, subscriptionId, params);
    }

    @Override
    public void onUpdateSubscribe(WsRequestMsg updateSubscribeRequest, String accountId, String userId, Map<String, Object> params) {
        super.onUpdateSubscribe(updateSubscribeRequest, accountId, userId, params);
    }

    @Override
    public void onEvent(Object event, Map<String, Object> params) {
        super.onEvent(event, params);
    }

    @Override
    public void onEvent(Object event, String accountId, String userId, Map<String, Object> params) {
        super.onEvent(event, accountId, userId, params);
    }

    private void onSubscribeSender(String subscribeId) {
        try {
            sender.send(ExConversationChangeNotification.example());
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

        //Run modifiers
        for(SubscriptionResultModifier<ExtendedConversation,ExtendedConversation> rsModify : aamResultModifiers){
            rsModify.modify(exConv, params);
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
