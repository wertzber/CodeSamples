package subscription.test;

import com.liveperson.api.RespBody;
import com.liveperson.api.ams.aam.ExConversationChangeNotification;
import com.liveperson.api.ams.aam.SubscribeExConversations;
import com.liveperson.api.ams.aam.SubscribeExConversationsBuilder;
import com.liveperson.api.server.Remote;
import com.liveperson.api.server.RequestMsg;
import com.liveperson.api.server.ResponseMsg;
import com.liveperson.api.websocket.WsRequestMsg;
import com.liveperson.api.websocket.WsResponseMsg;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.impl.SubscriptionFilterManagerImpl;
import subscription.impl.SubscriptionServerAamImpl;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Matchers.any;

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
    public void aamSubsriptionTest(){
        QueueTestSender<String> sender = new QueueTestSender<>();
        SubscriptionServerAamImpl aamServerSubscriber = new SubscriptionServerAamImpl(
                new SubscriptionFilterManagerImpl(), sender);
        Runnable task1 = () -> {
            sender.run();

        };
        new Thread(task1).start();
        long before = System.currentTimeMillis();
        for(int i=1; i<10000; i++ ){
            aamServerSubscriber.onSubscribe(subscribeReq, "brand1", "user" + i, null);
        }
        long aftre = System.currentTimeMillis();
        logger.info("Execution: {} msec", aftre-before );
    }
}
