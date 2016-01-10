package subscription.impl;

import subscription.api.SubscriptionExecutor;
import subscription.api.SubscriptionActions;

/**
 * Created by eladw on 1/5/2016.
 */
public class SubscribeExecutorImpl<Input> implements SubscriptionExecutor<Input> {

    SubscriptionActions subscriptionActions;

    public SubscribeExecutorImpl(SubscriptionActions subscriptionActions){
        this.subscriptionActions = subscriptionActions;
    }


    @Override
    public boolean executeSubscriptionPredicateOnAll(Input input) {
        return true; //not relevant for now
    }

    @Override
    public boolean executeSubscriptionPredicateOnAccount(String account,Input input) {
        return true;
//        return subscriptionActions.getAccountSubscriptions(account)
//                .entrySet()
//                .stream()
//                .filter(new AamPredicate())
//                .map();
    }

    @Override
    public boolean executeSubscriptionPredicateOnSubsId(String account, String subscriptionId, Input input) {
        return false;
    }

    @Override
    public boolean executeSubscriptionPredicateOnUser(String account, String userId, Input input) {
        return false;
    }

    @Override
    public boolean executeOnHistory(Input input) {
        return false;
    }
}
