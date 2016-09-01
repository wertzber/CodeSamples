package guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.pojo.BillingService;
import guice.service.BillingModule;

/**
 * Created by eladw on 8/31/2016.
 */
public class Main {

    public static Injector injector  = Guice.createInjector(new BillingModule());;
    public static void main(String[] args) {
        /*
     * Guice.createInjector() takes your Modules, and returns a new Injector
     * instance. Most applications will call this method exactly once, in their
     * main() method.
     */


    /*
     * Now that we've got the injector, we can build objects.
     */
        BillingService billingService = injector.getInstance(BillingService.class);

        billingService.chargeOrder(100);
    }
}
