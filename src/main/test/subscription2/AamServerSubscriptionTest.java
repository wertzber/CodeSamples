package subscription2;

import com.liveperson.api.ams.aam.SubscribeExConversations;
import com.liveperson.api.ams.aam.SubscribeExConversationsBuilder;
import com.liveperson.api.ams.aam.UnsubscribeExConversations;
import com.liveperson.api.ams.aam.types.ExtendedConversationDetails;
import com.liveperson.api.ams.aam.types.ExtendedConversationDetailsBuilder;
import com.liveperson.api.ams.cm.types.ConversationDetailsBuilder;
import com.liveperson.api.ams.cm.types.ConversationState;
import com.liveperson.api.ams.cm.types.ParticipantRole;
import com.liveperson.api.ams.types.TTR;
import com.liveperson.api.websocket.WsRequestMsg;
import com.liveperson.api.websocket.WsResponseMsg;
import com.liveperson.messaging.async.types.cm.entities.Conversation;
import com.liveperson.messaging.async.types.cm.entities.ConversationBuilder;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.SubscriptionResultModifier;
import subscription.converter.AamConverterIn;
import subscription.converter.AamConverterOut;
import subscription.data.aam.ExtendedConversation;
import subscription.data.subscribe.SubscriptionData;
import subscription.impl.SubscriptionServerAamImpl;
import subscription.modifier.AamModifierNoteOut;
import subscription.test.QueueTestSender;
import subscription.utils.SubscriptionConsts;

import javax.ws.rs.core.Response;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * Created by eladw on 1/6/2016.
 */
public class AamServerSubscriptionTest {

    private static final Logger logger = LoggerFactory.getLogger(AamServerSubscriptionTest.class);

    private WsRequestMsg subscribeReq;
    WsResponseMsg wsResponseMsg;

    //in predicate
    Predicate<Conversation> nonNullPredicate = Objects::nonNull;

    Predicate<Conversation> testConversationState = (Conversation conv)-> {
        return ConversationState.OPEN.equals(conv.state);
    };
    Predicate<Conversation> testConversationBrand = (Conversation conv)-> {
        return conv.brandId!=null;
    };

    Map<String,Object> params = new HashMap<>();



    //modifer
    Predicate<String> predicate = (role) ->{
        return role.equals("CONSUMER");
    };

    BiFunction<ExtendedConversationDetails, String, ExtendedConversationDetails> action = (ExtendedConversationDetails exConv, String value) ->{
        ExtendedConversationDetails exConUpdate = null;
        if(exConv != null){
            exConUpdate = new ExtendedConversationDetailsBuilder()
                    .withConversationDetails(new ConversationDetailsBuilder().copy(exConv.conversationDetails).withNote(value).build())
                    .build();
        }
        return exConUpdate;
    };


    @Before
    public void setupMock() {

        subscribeReq = new WsRequestMsg("123", new SubscribeExConversationsBuilder()
                        .withBrandId("brand1")
                        .withConvState(EnumSet.of(ConversationState.OPEN))
                        .build());
        wsResponseMsg =  new WsResponseMsg(
                "11",
                Response.Status.OK,
                new SubscribeExConversations.Response("11"));
        params.put(SubscriptionConsts.ROLE, ParticipantRole.CONSUMER.toString());

    }

    @Test
    public void aamSubsUnsubBaseTest(){
        QueueTestSender<String> sender = new QueueTestSender<>();
        Predicate filtersIn = nonNullPredicate.and(testConversationState)
                .and(testConversationBrand);

        List<SubscriptionResultModifier> modifiers = new ArrayList<>();

        modifiers.add(new AamModifierNoteOut(predicate, null, action));

        SubscriptionServerAamImpl aamServerSubscriber = new SubscriptionServerAamImpl(
                filtersIn,
                modifiers,
                new AamConverterIn(),
                new AamConverterOut(),
                sender);

        Runnable task1 = () -> {
            sender.run();

        };
        new Thread(task1).start();
        long before = System.currentTimeMillis();
        for(int i=1; i<=1; i++ ){
            aamServerSubscriber.onSubscribe(subscribeReq, "brand1", "user" + i, null);
        }
        long aftre = System.currentTimeMillis();
        logger.info("Execution: {} msec", aftre - before);
        int size = aamServerSubscriber.getAamSubscriptionActions().getAccountSubscriptions("brand1").size();
        Assert.assertEquals("Some subscribers were not subscribed ", 1, size);


        Conversation conversation = new ConversationBuilder()
                .withBrandId("brand1")
                .withConvId("conv1")
                .withStartTs(1l)
                .withState(ConversationState.OPEN)
                .withTtr(new TTR())
                .build();
        aamServerSubscriber.onEvent(conversation, "brand1", null, params);
        aamServerSubscriber.onEvent(conversation, "brand1", null, params);
        aamServerSubscriber.onEvent(conversation, "brand1", null, params);

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //unsubscribe
        List<SubscriptionData<ExtendedConversation, SubscribeExConversations>> userSubscriptions = aamServerSubscriber.
                getAamSubscriptionActions().getUserSubscriptions("brand1", "user1");
        SubscriptionData<ExtendedConversation, SubscribeExConversations> subs = userSubscriptions.get(0);
        subscribeReq = new WsRequestMsg("123", new UnsubscribeExConversations(subs.getSubscriptionId()));

        aamServerSubscriber.onUnSubscribe(subscribeReq, "brand1", "user1", null);
        size = aamServerSubscriber.getAamSubscriptionActions().getAccountSubscriptions("brand1").size();
        Assert.assertEquals("Some unsubscribers were not unsubscribed ", 0, size);


    }
}
