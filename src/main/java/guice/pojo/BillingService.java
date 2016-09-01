package guice.pojo;

import com.google.inject.Inject;

/**
 * Created by eladw on 8/31/2016.
 */
public interface BillingService {

     void chargeOrder(long howMuchToCharge);

}
