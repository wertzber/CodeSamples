package subscription2.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: ofirp
 * Date: 1/25/2016
 * Time: 12:01 PM
 */
public class SubscriptionAlreadyExistsException extends Exception {

    public SubscriptionAlreadyExistsException(String subscriptionId) {
        super("Subscription already exists " + subscriptionId);
    }
}
