package guice.example5.pojo;

/**
 * Created by eladw on 8/31/2016.
 */
public class FakeBillingService implements BillingService {


    @Override
    public void chargeOrder(long howMuchToCharge) {
        System.out.println("Fake @@@ billingService Change order");
    }
}
