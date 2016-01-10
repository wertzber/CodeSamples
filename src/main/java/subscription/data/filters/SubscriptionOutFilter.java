package subscription.data.filters;

import subscription.api.SubscriptionFilter;

/**
 * Created by eladw on 1/9/2016.
 */
public class SubscriptionOutFilter implements SubscriptionFilter {
    @Override
    public boolean filter(Object eventToTest) {
        return true;
    }
}
