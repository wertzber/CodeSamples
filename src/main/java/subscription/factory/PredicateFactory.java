package subscription.factory;

import com.liveperson.api.ReqBody;
import com.liveperson.api.ams.aam.SubscribeExConversations;
import subscription.data.aam.AamPredicate;

import java.util.function.Predicate;

/**
 * Created by eladw on 1/6/2016.
 */
public class PredicateFactory {
    public static Predicate getPredicate(Class predicateClass, Object origRequest) {
        if (AamPredicate.class.getName().equals(predicateClass.getName())) {
            // todo need to find a safer way than casting
            return new AamPredicate((SubscribeExConversations)origRequest);
        } else {
            throw new IllegalStateException("Unsupported object type " + predicateClass.getName());
        }
    }
}
