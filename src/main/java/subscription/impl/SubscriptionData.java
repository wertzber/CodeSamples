package subscription.impl;

import java.util.function.Predicate;

/**
 * T - predicate object to use
 * O - orig subscribe
 *
 * predicate - actual predicate to use
 * origSubscribe - explanation of the subscribe
 * User: eyali
 * Date: 7/29/2015
 * Time: 2:30 PM
 */

public class SubscriptionData<T,O> {

    private Predicate<T> subscribePredicate;
    private O origSubscribe;
    //subscriptionId of this query
    private String subscriptionId;

    public SubscriptionData(Predicate<T> subscribePredicate, O origSubscribe,
                            String subscriptionId) {
        this.subscribePredicate = subscribePredicate;
        this.origSubscribe = origSubscribe;
        this.subscriptionId = subscriptionId;
    }

    public Predicate<T> getSubscribePredicate() {
        return subscribePredicate;
    }
    public O getOrigSubscribe() {
        return origSubscribe;
    }

    @Override
    public String toString() {
        return "SubscriptionData{" +
                "subscriptionId='" + subscriptionId + '\'' +
                ", origSubscribe=" + origSubscribe +
                '}';
    }
}
