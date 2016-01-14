package subscription.data.filters;

import com.liveperson.api.ams.aam.SubscribeExConversations;
import subscription.api.SubscriptionFilter;

/**
 * Created by eladw on 1/9/2016.
 */
public class SubscriptionInFilter implements SubscriptionFilter {

    @Override
    public boolean filter(Object eventToTest) {

        if(eventToTest instanceof SubscribeExConversations){
            if(((SubscribeExConversations)eventToTest).brandId!=null) return true;
            else return false;
        } else {
            //skip
            return true;
        }
    }


}
