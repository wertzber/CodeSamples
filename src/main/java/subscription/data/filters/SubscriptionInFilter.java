package subscription.data.filters;

import com.liveperson.api.ams.aam.SubscribeExConversations;
import subscription.api.SubscriptionFilter;

/**
 * Created by eladw on 1/9/2016.
 */
public class SubscriptionInFilter implements SubscriptionFilter<SubscribeExConversations> {



    @Override
    public boolean filter(SubscribeExConversations eventToTest) {
        if(eventToTest.brandId!=null) return true;
        return false;
    }


}
