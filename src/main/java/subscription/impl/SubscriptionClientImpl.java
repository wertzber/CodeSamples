package subscription.impl;

import subscription.api.SubscriptionClient;

/**
 * Created by eladw on 1/4/2016.
 * Client subscription.
 * TODO: Should also support retry options.
 */
public class SubscriptionClientImpl<R,N> implements SubscriptionClient<R,N> {


    @Override
    public void onSubscriptionResponse(R Response) {

    }

    @Override
    public void onNotify(N Notify) {

    }

    @Override
    public void onUnSubscription(R Response) {

    }


}
