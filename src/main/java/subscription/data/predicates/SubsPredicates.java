package subscription.data.predicates;

import com.liveperson.api.ams.aam.SubscribeExConversations;

import java.util.function.Predicate;

/**
 * Created by eladw on 1/9/2016.
 * Gather all kind of predicates
 */
public final class SubsPredicates {

    private SubsPredicates(){}

    public static Predicate<SubscribeExConversations> isSubsBrandExists() {
        return subs -> subs.brandId != null;
    }


}
