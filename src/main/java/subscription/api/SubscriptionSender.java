package subscription.api;

import java.util.List;

/**
 * Created by eladw on 1/6/2016.
 * Base interface for the sender of the subscription util
 */
public interface SubscriptionSender {

    void send(String clientId, Object dataToSend);

    default void send(String clientId, List<Object> dataToSend) {
        dataToSend.forEach(data -> this.send(clientId, data));
    }

}
