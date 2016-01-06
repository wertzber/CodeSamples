package subscription.api;

import java.util.List;

/**
 * Created by eladw on 1/4/2016.
 * Basic actions for subscribe. could be client or server.
 * S - represents subscribe object.
 */
public interface BaseSubscriber<S> {

    List<String> getSubscriptionsIds(); //Get all subscription Ids
    S getSubscribeById(String id);
    S getSubscribeByHierarchyIds(List<String> ids);


}
