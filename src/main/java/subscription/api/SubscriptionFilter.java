package subscription.api;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by eladw on 1/9/2016.
 * Each Filter will have Base filters.
 * User can extend the base filter.
 *
 * Is valid will be used for the actual validation.
 */
public interface SubscriptionFilter<T> {
    boolean filter(T eventToTest);
}
