package subscription.api;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by eladw on 1/4/2016.
 * Used for filter the incoming notification
 */
public interface NotificationFilter<F> {

    void addFilters(List<F> filtersToAdd);
    //TODO - move to the filter. boolean isValid(F notification);
    void removeFilters(List<F> filtersToRemove);
}
