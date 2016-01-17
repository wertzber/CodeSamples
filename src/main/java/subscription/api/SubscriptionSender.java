package subscription.api;

import java.util.List;

/**
 * Created by eladw on 1/6/2016.
 * Base interface for the sender of the subscription util
 */
public interface SubscriptionSender<T> {

    void send(T dataToSend) throws InterruptedException;
    void send(List<T> dataToSend) throws InterruptedException;

}
