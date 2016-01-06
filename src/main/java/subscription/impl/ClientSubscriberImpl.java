package subscription.impl;

import subscription.api.ClientSubscriber;

/**
 * Created by eladw on 1/4/2016.
 * Client subscription.
 * TODO: Should also support retry options.
 */
public class ClientSubscriberImpl<R,N> implements ClientSubscriber<R,N> {


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
