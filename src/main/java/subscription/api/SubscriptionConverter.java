package subscription.api;

import com.liveperson.api.ams.aam.types.ExtendedConversationDetails;

import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * Created by eladw on 1/14/2016.
 * convert data type. could be used: in flow / out flow
 */
public interface SubscriptionConverter<IN,OUT> {

    OUT convert(IN input);

    OUT convert(String subscribeId, IN input);

}