package guice.example5;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;
import guice.example5.pojo.BillingService;
import guice.example5.pojo.Report;

/**
 * Created by eladw on 8/31/2016.
 */
public class Main {

    @Inject
    public Report report;

    public static Injector injector  = Guice.createInjector(new BillingModule());;


    public static void main(String[] args) {

    /*
     * Now that we've got the injector, we can build objects.
     */
        BillingService billingService = injector.getInstance(BillingService.class);
        System.out.println(billingService);
        BillingService billingService2 = injector.getInstance(BillingService.class);
        System.out.println(billingService2);
        billingService.chargeOrder(100);
        Main main = new Main();
        main.printReport();
        Main main2 = new Main();
        main2.printReport();
    }

    public void printReport(){
        System.out.println(report);

    }

}
