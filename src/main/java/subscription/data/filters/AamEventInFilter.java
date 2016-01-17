package subscription.data.filters;

import com.liveperson.api.ams.aam.SubscribeExConversations;
import com.liveperson.messaging.async.types.cm.entities.Conversation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.SubscriptionFilter;

/**
 * Created by eladw on 1/9/2016.
 */
public class AamEventInFilter implements SubscriptionFilter {

    private static final Logger logger = LoggerFactory.getLogger(AamEventInFilter.class);

    @Override
    public boolean filter(Object eventInput) {
        if(eventInput instanceof Conversation){
            Conversation event = (Conversation)eventInput;
            if(event.brandId!=null && event.startTs > 0) return true;
            else return false;
        } else {
            //skip
            return true;
        }
    }
}
