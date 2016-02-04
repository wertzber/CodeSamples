package subscription.data.subscribe;

import java.util.function.Predicate;

/**
 * P
 * O - orig subscribe
 *
 * predicate - actual predicate to use
 * origSubscribe - explanation of the subscribe
 * User: eyali
 * Date: 7/29/2015
 * Time: 2:30 PM
 */

public class SubscriptionData<O,P> {

    private Predicate<P> subscribePredicate;
    private O origSubscribe;
    //subscriptionId of this query
    private String subscriptionId;
    private String clientId;

    public SubscriptionData(Predicate<P> subscribePredicate, O origSubscribe,
                            String subscriptionId, String clientId) {
        this.subscribePredicate = subscribePredicate;
        this.origSubscribe = origSubscribe;
        this.subscriptionId = subscriptionId;
        this.clientId = clientId;
    }

    public Predicate<P> getSubscribePredicate() {
        return subscribePredicate;
    }
    public O getOrigSubscribe() {
        return origSubscribe;
    }
    public String getSubscriptionId() {return subscriptionId;};
    public String getClientId() {
        return clientId;
    }

    @Override
    public String toString() {
        return "SubscriptionData{" +
                "subscribePredicate=" + subscribePredicate +
                ", origSubscribe=" + origSubscribe +
                ", subscriptionId='" + subscriptionId + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
