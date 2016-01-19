package subscription.impl;

import com.liveperson.api.ams.aam.ExConversationChangeNotification;
import com.liveperson.api.ams.aam.SubscribeExConversations;
import com.liveperson.api.ams.aam.types.ExtendedConversationDetails;
import com.liveperson.api.ams.aam.types.ExtendedConversationDetailsBuilder;
import com.liveperson.api.ams.cm.types.ConversationDetails;
import com.liveperson.api.ams.cm.types.ConversationDetailsBuilder;
import com.liveperson.api.ams.cm.types.ConversationState;
import com.liveperson.api.server.RequestMsg;
import com.liveperson.api.websocket.WsRequestMsg;
import com.liveperson.messaging.async.types.cm.entities.Conversation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.*;
import subscription.data.FilterType;
import subscription.data.aam.AamPredicate;
import subscription.data.aam.ExtendedConversation;
import subscription.data.aam.ExtendedConversationBuilder;
import subscription.data.subscribe.SubscriptionData;
import subscription.factory.PredicateFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by eladw on 1/4/2016.
 * Implement specific AAM sunsription server
 */
public class SubscriptionServerAamImpl implements SubscriptionServer<WsRequestMsg> {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionServerAamImpl.class);

    SubscriptionActions<ExtendedConversation, SubscribeExConversations> aamSubscriptionActions = new SubscriberActionsImpl();

    private SubscriptionFilterManager<SubscriptionFilter> aamFilterManager;
    private SubscriptionConverter<Conversation, ExtendedConversation> incomingEventsConverter;
    private SubscriptionConverter<ExtendedConversationDetails, ExConversationChangeNotification> outgoingEventsConverter;
    private SubscriptionSender sender;

    public SubscriptionServerAamImpl(SubscriptionFilterManager<SubscriptionFilter> subscriptionFilterManager,
                                     SubscriptionConverter<Conversation, ExtendedConversation> incomingEventsConverter,
                                     SubscriptionConverter<ExtendedConversationDetails,ExConversationChangeNotification> outgoingEventsConverter,
                                     SubscriptionSender sender){

        this.aamFilterManager = subscriptionFilterManager;
        this.incomingEventsConverter = incomingEventsConverter;
        this.outgoingEventsConverter = outgoingEventsConverter;
        this.sender = sender;

    }

    @Override
    public void onSubscribe(WsRequestMsg inSubscribeRequest,String accountId, String userId,
                            Map<String, String> params) {
        SubscribeExConversations inSubscribeBody = (SubscribeExConversations) inSubscribeRequest.body;
        boolean filterRes = aamFilterManager.testFilters(FilterType.IN, inSubscribeBody);
        if(filterRes){

            String subsId = aamSubscriptionActions.addSubscriber(
                    accountId, userId, new AamPredicate(inSubscribeBody), inSubscribeBody);
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
    public void onUnSubscribe(WsRequestMsg inUnSubscribeRequest, final String account, String userId, Map<String, String> params) {
        if(userId!=null || account==null){
            aamSubscriptionActions.removeSubscriberUser(account, userId);
        } else {
            logger.warn("Failed to do unsubscribe, wrong input: userId:" + userId + " account:" + account);
        }
    }

    @Override
    public void onUpdateSubscribe(WsRequestMsg updateSubscribeRequest, final String account, String userId,
                                  Map<String, String> params) {

    }

    @Override
    public void onEvent(Object event, String account, String userId, Map<String, String> params ) {
        ExtendedConversation exConv = null;
        //filter in
        boolean isValid = getAamFilterManager().testFilters(FilterType.IN, event);
        if(isValid){
            //convertFormat
            if(event instanceof Conversation){
                exConv = this.incomingEventsConverter.convert((Conversation) event);
            } else {
                logger.error("Unsupported incoming event " + event.getClass());
            }

            //execute predicate
            Map<String, SubscriptionData<ExtendedConversation, SubscribeExConversations>> accountSubscriptions =
                    aamSubscriptionActions.getAccountSubscriptions(account);

            final ExtendedConversationDetails finalExConvDetails = new ExtendedConversationDetailsBuilder()
                    .withConvId(exConv.getAamConversation().convId)
                    .withConversationDetails(new ConversationDetailsBuilder()
                                            .withConvId(exConv.getAamConversation().convId)
                                            .withBrandId(exConv.getAamConversation().brandId)
                                            .withNote("convert out")
                            .withState(exConv.getAamConversation().state)
                                            .build())
                    .build();

            final ExtendedConversation finalExConv = exConv;
            List<ExConversationChangeNotification> notifications = accountSubscriptions.entrySet().stream()
                    .filter(val -> {
                        return val.getValue().getSubscribePredicate().test(finalExConv);
                    })
                    //convert out
                    .map(key -> this.outgoingEventsConverter.convert(key.getValue().getSubscriptionId(), finalExConvDetails))
                    .collect(Collectors.toList());

            //filter out - not implemented yet

            //send
            try {
                sender.send(notifications);
            } catch (InterruptedException e) {
                logger.info("failed to send msg ", e);
            }
        } else {
            logger.warn("Not valid input event {}", event );
        }

    }

    public SubscriptionActions<ExtendedConversation, SubscribeExConversations> getAamSubscriptionActions() {
        return aamSubscriptionActions;
    }

    public SubscriptionFilterManager<SubscriptionFilter> getAamFilterManager() {
        return aamFilterManager;
    }

    public SubscriptionSender getSender() {
        return sender;
    }
}
