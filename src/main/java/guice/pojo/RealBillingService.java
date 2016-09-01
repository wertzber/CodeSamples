package guice.pojo;

/**
 * Created by eladw on 8/31/2016.
 */
public class RealBillingService implements BillingService {


    @Override
    public void chargeOrder(long howMuchToCharge) {
        System.out.println("Real billingService Change order");
    }
}
