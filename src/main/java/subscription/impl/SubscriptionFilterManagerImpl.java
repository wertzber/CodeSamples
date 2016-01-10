package subscription.impl;

import subscription.api.SubscriptionFilterManager;
import subscription.api.SubscriptionFilter;
import subscription.data.filters.SubscriptionInFilter;
import subscription.data.filters.SubscriptionOutFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eladw on 1/9/2016.
 *
 */
public class SubscriptionFilterManagerImpl implements SubscriptionFilterManager<SubscriptionFilter> {
    //Have one map. in and out.
    //Each key is the type of action(in/out) and it have list of filters to use
    Map<FilterType, List<SubscriptionFilter>> filters = new ConcurrentHashMap();

    public SubscriptionFilterManagerImpl() {
        List<SubscriptionFilter> inSubsFilters  = new ArrayList<>();
        inSubsFilters.add(new SubscriptionInFilter());
        addFilters(FilterType.IN, inSubsFilters );
        List<SubscriptionFilter> outSubsFilters  = new ArrayList<>();
        outSubsFilters.add(new SubscriptionOutFilter());
        addFilters(FilterType.OUT, outSubsFilters );


    }

    @Override
    public void addFilters(FilterType filterType, List<SubscriptionFilter> filtersToAdd) {
                filters.computeIfAbsent(filterType, val -> new ArrayList())
                        .addAll(filtersToAdd);
    }

    @Override
    public void removeFilters(FilterType filterType, List<SubscriptionFilter> filtersToRemove) {

    }

    @Override
    public <I>boolean testFilters(FilterType filterType, I input) {

        boolean isValid = false;
        List<SubscriptionFilter> filterList = filters.get(filterType);
        for(SubscriptionFilter subsFilter : filterList){
            if(!subsFilter.filter(input)){
                isValid = false;
                return isValid;
            }
        }
        isValid = true;
        return isValid;
    }
}
