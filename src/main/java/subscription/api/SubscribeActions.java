package subscription.api;

/**
 * Created by eladw on 1/4/2016.
 * Query is a single subscription data. According to the subscription app will decide if to send notification
 * There could be many.
 * This class in charge of actions which related to query.
 */
public interface SubscribeActions<T> {


    boolean removeSubscriber(String Id);
    boolean removeSubscriber(String mainId, String secondaryId);

    boolean addSubscriber(String Id, T queryObject);
    boolean addSubscriber(String mainId, String SecondaryId, T queryObject);

    boolean executeSubscriptionPredicateOnAll();
    boolean executeSubscriptionPredicateOnId(String id);



}
