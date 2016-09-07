package guice.example1;

import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.example1.pojo.BillingService;
import guice.example1.service.BillingModule;

/**
 * Created by eladw on 8/31/2016.
 */
public class Main {

    public static Injector injector  = Guice.createInjector(new BillingModule());;
    public static void main(String[] args) {
        //diff objects
        BillingService billingService = injector.getInstance(BillingService.class);
        System.out.println(billingService);
        BillingService billingService2 = injector.getInstance(BillingService.class);
        System.out.println(billingService2);
        billingService.chargeOrder(100);
    }
}
