package subscription.impl;

import subscription.api.SubscribeExecutor;
import subscription.api.SubscriberActions;
import subscription.data.aam.AamPredicate;

/**
 * Created by eladw on 1/5/2016.
 */
public class SubscribeExecutorImpl<Input> implements SubscribeExecutor<Input> {

    SubscriberActions subscriberActions;

    public SubscribeExecutorImpl(SubscriberActions subscriberActions){
        this.subscriberActions = subscriberActions;
    }


    @Override
    public boolean executeSubscriptionPredicateOnAll(Input input) {
        return true; //not relevant for now
    }

    @Override
    public boolean executeSubscriptionPredicateOnAccount(String account,Input input) {
        return true;
//        return subscriberActions.getAccountSubscriptions(account)
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
