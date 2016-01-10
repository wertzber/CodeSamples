package subscription.api;

/**
 * Created by eladw on 1/6/2016.
 * Base interface for the sender of the subscription util
 */
public interface SubscriptionSender<T> {

    void send(T dataToSend) throws InterruptedException;

}
