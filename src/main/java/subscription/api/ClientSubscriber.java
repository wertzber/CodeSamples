package subscription.api;

/**
 * Created by eladw on 1/4/2016.
 * Interface for the client subscriber
 * R - Response
 * N- Notify
 */
public interface ClientSubscriber<R,N> {

    public void onSubscriptionResponse(R Response); //Action after subscriber response ( success/fail)
    public void onNotify(N Notify);                 //Action for each notify
    public void onUnSubscription(R Response);       //Action after un subscriber(succes/fail)



}
