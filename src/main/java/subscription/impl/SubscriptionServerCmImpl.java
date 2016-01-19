package subscription.impl;

import com.liveperson.api.ams.aam.ExConversationChangeNotification;
import com.liveperson.api.ams.aam.SubscribeExConversations;
import com.liveperson.api.ams.aam.types.ExtendedConversationDetails;
import com.liveperson.api.ams.aam.types.ExtendedConversationDetailsBuilder;
import com.liveperson.api.ams.cm.ConversationChangeNotification;
import com.liveperson.api.ams.cm.SubscribeConversations;
import com.liveperson.api.ams.cm.types.ConversationDetails;
import com.liveperson.api.ams.cm.types.ConversationDetailsBuilder;
import com.liveperson.api.websocket.WsRequestMsg;
import com.liveperson.messaging.async.types.cm.entities.Conversation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.*;
import subscription.data.FilterType;
import subscription.data.aam.AamPredicate;
import subscription.data.aam.ExtendedConversation;
import subscription.data.cm.ConversationPredicate;
import subscription.data.subscribe.SubscriptionData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by eladw on 1/4/2016.
 * Implement specific AAM sunsription server
 */
public class SubscriptionServerCmImpl implements SubscriptionServer<WsRequestMsg> {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionServerCmImpl.class);

    SubscriptionActions<Conversation, SubscribeConversations> cmSubscriptionActions = new SubscriberActionsImpl();

    private SubscriptionFilterManager<SubscriptionFilter> cmFilterManager;
    private SubscriptionConverter<Conversation, ConversationDetails> incomingEventsConverter;
    private SubscriptionConverter<ConversationDetails, ConversationChangeNotification> outgoingEventsConverter;
    private SubscriptionSender sender;

    public SubscriptionServerCmImpl(SubscriptionFilterManager<SubscriptionFilter> subscriptionFilterManager,
                                    SubscriptionConverter<Conversation, ConversationDetails> incomingEventsConverter,
                                    SubscriptionConverter<ConversationDetails, ConversationChangeNotification> outgoingEventsConverter,
                                    SubscriptionSender sender){

        this.cmFilterManager = subscriptionFilterManager;
        this.incomingEventsConverter = incomingEventsConverter;
        this.outgoingEventsConverter = outgoingEventsConverter;
        this.sender = sender;

    }

    @Override
    public void onSubscribe(WsRequestMsg inSubscribeRequest,String accountId, String userId,
                            Map<String, String> params) {
        SubscribeConversations inSubscribeBody = (SubscribeConversations) inSubscribeRequest.body;
        boolean filterRes = cmFilterManager.testFilters(FilterType.IN, inSubscribeBody);
        if(filterRes){

            String subsId = cmSubscriptionActions.addSubscriber(
                    accountId, userId, new ConversationPredicate(inSubscribeBody), inSubscribeBody);
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
            cmSubscriptionActions.removeSubscriberUser(account, userId);
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
        ConversationDetails convDetails = null;
        //filter in
        boolean isValid = getAamFilterManager().testFilters(FilterType.IN, event);
        if(isValid){
            //convertFormat
            if(event instanceof Conversation){
                convDetails = this.incomingEventsConverter.convert((Conversation) event);
            } else {
                logger.error("Unsupported incoming event " + event.getClass());
            }

            //execute predicate
            Map<String, SubscriptionData<Conversation, SubscribeConversations>> accountSubscriptions =
                    cmSubscriptionActions.getAccountSubscriptions(account);

            final ConversationDetails finalConvDetails = new ConversationDetailsBuilder()
                    .withConvId(convDetails.convId)
                    .withBrandId(convDetails.brandId)
                    .withNote("convert out cm")
                    .withState(convDetails.state)
                    .build();


            List<ConversationChangeNotification> notifications = accountSubscriptions.entrySet().stream()
                    .filter(val -> {
                        return val.getValue().getSubscribePredicate().test((Conversation) event);
                    })
                    //convert out
                    .map(key -> this.outgoingEventsConverter.convert(key.getValue().getSubscriptionId(),
                            finalConvDetails))
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

    public SubscriptionActions<Conversation, SubscribeConversations> getCmSubscriptionActions() {
        return cmSubscriptionActions;
    }

    public SubscriptionFilterManager<SubscriptionFilter> getAamFilterManager() {
        return cmFilterManager;
    }

    public SubscriptionSender getSender() {
        return sender;
    }
}
