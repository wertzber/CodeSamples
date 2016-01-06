package subscription.api;

import java.util.List;

/**
 * Created by eladw on 1/4/2016.
 * Execute the test in order to get list of id's.
 * The list represents who are the subscribers that a server
 * should create result for it
 */
public interface SubscribeExecutor<Input> {

    boolean executeSubscriptionPredicateOnAll(Input input);
    boolean executeSubscriptionPredicateOnAccount(String account, Input input);
    boolean executeSubscriptionPredicateOnSubsId(String account, String subscriptionId, Input input);
    boolean executeSubscriptionPredicateOnUser(String account, String userId, Input input);
    boolean executeOnHistory(Input input);


}
