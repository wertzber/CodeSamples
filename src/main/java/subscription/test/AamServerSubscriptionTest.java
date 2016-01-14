package subscription.test;

import com.liveperson.api.ams.aam.SubscribeExConversations;
import com.liveperson.api.ams.aam.SubscribeExConversationsBuilder;
import com.liveperson.api.ams.aam.UnsubscribeExConversations;
import com.liveperson.api.websocket.WsRequestMsg;
import com.liveperson.api.websocket.WsResponseMsg;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.SubscriptionFilter;
import subscription.api.SubscriptionFilterManager;
import subscription.data.FilterType;
import subscription.data.aam.AamPredicate;
import subscription.data.filters.AamEventInFilter;
import subscription.data.subscribe.SubscriptionData;
import subscription.impl.SubscriptionFilterManagerImpl;
import subscription.impl.SubscriptionServerAamImpl;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
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
                        .withBrandId(counter.toString()).build());
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
                new SubscriptionFilterManagerImpl(), sender);
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

        //unsubscribe
        List<String> userSubscriptions = aamServerSubscriber.
                getAamSubscriptionActions().getUserSubscriptions("brand1", "user1");
        String subsId = userSubscriptions.get(0);
        subscribeReq = new WsRequestMsg("123", new UnsubscribeExConversations(subsId));

        aamServerSubscriber.onUnSubscribe(subscribeReq,"brand1","user1", null);
        size = aamServerSubscriber.getAamSubscriptionActions().getAccountSubscriptions("brand1").size();
        Assert.assertEquals("Some unsubscribers were not unsubscribed ", 0, size);

    }
}
