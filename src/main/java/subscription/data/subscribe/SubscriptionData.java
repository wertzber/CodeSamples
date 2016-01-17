package subscription.data.subscribe;

import subscription.data.aam.ExtendedConversation;

import java.util.function.Predicate;

/**
 * P - predicate object to use, for example: in aam use ExtendConv
 * O - orig subscribe
 *
 * predicate - actual predicate to use
 * origSubscribe - explanation of the subscribe
 * User: eyali
 * Date: 7/29/2015
 * Time: 2:30 PM
 */

public class SubscriptionData<P,O> {

    private Predicate<P> subscribePredicate;
    private O origSubscribe;
    //subscriptionId of this query
    private String subscriptionId;

    public SubscriptionData(Predicate<P> subscribePredicate, O origSubscribe,
                            String subscriptionId) {
        this.subscribePredicate = subscribePredicate;
        this.origSubscribe = origSubscribe;
        this.subscriptionId = subscriptionId;
    }

    public Predicate<P> getSubscribePredicate() {
        return subscribePredicate;
    }
    public O getOrigSubscribe() {
        return origSubscribe;
    }
    public String getSubscriptionId() {return subscriptionId;};

    @Override
    public String toString() {
        return "SubscriptionData{" +
                "subscriptionId='" + subscriptionId + '\'' +
                ", origSubscribe=" + origSubscribe +
                '}';
    }
}
