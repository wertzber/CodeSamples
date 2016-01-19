package subscription.test.aam;

import com.liveperson.api.ams.aam.SubscribeExConversations;
import com.liveperson.api.ams.aam.SubscribeExConversationsBuilder;
import com.liveperson.api.ams.aam.UnsubscribeExConversations;
import com.liveperson.api.ams.cm.types.ConversationState;
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
import subscription.api.SubscriptionFilter;
import subscription.converter.AamConverterIn;
import subscription.converter.AamConverterOut;
import subscription.data.FilterType;
import subscription.data.aam.ExtendedConversation;
import subscription.data.filters.AamEventInFilter;
import subscription.data.subscribe.SubscriptionData;
import subscription.impl.SubscriptionFilterManagerImpl;
import subscription.impl.SubscriptionServerAamImpl;
import subscription.test.QueueTestSender;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by eladw on 1/6/2016.
 */
//@RunWith(PowerMockRunner.class)
public class AamServerSubscriptionTest {

    private static final Logger logger = LoggerFactory.getLogger(AamServerSubscriptionTest.class);

    private WsRequestMsg subscribeReq;

    private static AtomicInteger counter = new AtomicInteger();
    WsResponseMsg wsResponseMsg;
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



//        subscribeReq = Mockito.mock(RequestMsg.class);
//        Mockito.when(subscribeReq.getBody())
//                .thenReturn(new SubscribeExConversationsBuilder()
//                        .withBrandId(counter.toString()).build());

//        Mockito.when(subscribeReq.response(any(), any()))
//               .thenReturn(resp);
    }

    @Test
    public void aamSubsUnsubBaseTest(){
        QueueTestSender<String> sender = new QueueTestSender<>();
        SubscriptionServerAamImpl aamServerSubscriber = new SubscriptionServerAamImpl(
                new SubscriptionFilterManagerImpl(),
                new AamConverterIn(),
                new AamConverterOut(),
                sender);
        List<SubscriptionFilter> filters = new ArrayList<>();
        filters.add(new AamEventInFilter());
        aamServerSubscriber.getAamFilterManager().addFilters(FilterType.IN, filters);

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
        aamServerSubscriber.onEvent(conversation, "brand1", null, null);
        aamServerSubscriber.onEvent(conversation, "brand1", null, null);
        aamServerSubscriber.onEvent(conversation, "brand1", null, null);

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
