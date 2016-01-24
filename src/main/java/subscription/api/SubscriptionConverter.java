package subscription.api;

import com.liveperson.api.ams.aam.types.ExtendedConversationDetails;

import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * Created by eladw on 1/14/2016.
 * convert data type. could be used: in flow / out flow
 */
public interface SubscriptionConverter<IN,OUT> {

    default OUT convert(IN input) {
        System.err.print("SubscriptionConverter: convert not implemented");
        return null;
    }

    default OUT convert(String subscribeId, IN input) {
        System.err.print("SubscriptionConverter: convert(subscribeId,Input) not implemented ");
        return null;
    }

}