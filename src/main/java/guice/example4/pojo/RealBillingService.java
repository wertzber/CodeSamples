package guice.example4.pojo;

//import com.google.inject.Singleton;
import guice.example4.pojo.BillingService;

import javax.inject.Singleton;

/**
 * Created by eladw on 8/31/2016.
 */
//@Singleton
    @Singleton
public class RealBillingService implements BillingService {


    @Override
    public void chargeOrder(long howMuchToCharge) {
        System.out.println("Real billingService Change order");
    }
}
