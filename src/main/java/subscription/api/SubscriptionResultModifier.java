package subscription.api;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * Created by eladw on 1/14/2016.
 * convert data type. could be used: in flow / out flow
 */
public interface SubscriptionResultModifier<IN, OUT> {


    default OUT modify(IN inputEvent, Map<String,Object> params) {
        System.err.print("SubscriptionConverter: convert(in,predicate,action) not implemented ");
        return null;
    }
}