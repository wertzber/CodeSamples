package subscription.test;

import org.eclipse.jetty.util.BlockingArrayQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.SubscriptionSender;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by eladw on 1/9/2016.
 */
public class QueueTestSender<String> implements SubscriptionSender<String>, Runnable {

    private static final Logger logger = LoggerFactory.getLogger(QueueTestSender.class);

    protected BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

    @Override
    public void send(String textToSend) throws InterruptedException {
        queue.offer(textToSend, 2000, TimeUnit.SECONDS);
    }



    @Override
    public void run() {
        while (true){
               try {
                   String eventFromQueue = queue.poll(3000, TimeUnit.MILLISECONDS);
                   logger.info("### result: " + eventFromQueue);

               } catch (Exception e) {
                   logger.error("queue fetch failed ", e);
               }
           }
    }
}
