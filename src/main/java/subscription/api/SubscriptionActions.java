package subscription.api;

import subscription.data.subscribe.SubscriptionData;

import java.util.List;
import java.util.Map;

/**
 * Created by eladw on 1/4/2016.
 * Query is a single subscription data. According to the subscription app will decide if to send notification
 * There could be many.
 * This class in charge of actions which related to query.
 * T - predicate object to use
 * O - orig subscribe
 */
public interface SubscriptionActions<P,O> {

    boolean removeAccount(String account);   //remove
    boolean removeSubscriber(String account, String subscribeId);
    boolean removeSubscriberUser(String account, String userId);
    String addSubscriber(String account, String userId, O incomingSubscribe);
    Map<String, SubscriptionData<P, O>> getAccountSubscriptions(String account);
    SubscriptionData<P,O> getSubscription(String account, String subscribeId);
    List<String> getUserSubscriptions(String account, String userId);






}
