package subscription.api;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by eladw on 1/4/2016.
 * Used for filter the incoming notification
 * F - filters type
 * I - input to test
 */
public interface SubscriptionFilterManager<F> {

    public enum FilterType {IN, OUT};

    void addFilters(FilterType filterType, List<F> filtersToAdd);

    //TODO - move to the filter. boolean isValid(F notification);
    void removeFilters(FilterType filterType, List<F> filtersToRemove);

    <I>boolean testFilters (FilterType filterType, I input);
}
