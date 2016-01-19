package subscription.api;

import subscription.data.subscribe.SubscriptionData;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Created by eladw on 1/4/2016.
 * Query is a single subscription data. According to the subscription app will decide if to send notification
 * There could be many.
 * This class in charge of actions which related to query.
 * T - predicate object to use, such as ExConv in case of aam
 * O - orig subscribe
 */
public interface SubscriptionActions<T,O> {

    boolean removeAccount(String account);   //remove
    boolean removeSubscriber(String account, String subscribeId);
    boolean removeSubscriberUser(String account, String userId);
    String addSubscriber(String account, String userId, Predicate<T> predicate, O incomingSubscribe);
    Map<String, SubscriptionData<T, O>> getAccountSubscriptions(String account);
    SubscriptionData<T,O> getSubscription(String account, String subscribeId);
    List<SubscriptionData<T,O>> getUserSubscriptions(String account, String userId);






}
