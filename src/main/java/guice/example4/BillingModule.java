package guice.example4;

import com.google.inject.AbstractModule;
import guice.example4.pojo.BillingService;
import guice.example4.pojo.RealBillingService;

/**
 * Created by eladw on 8/31/2016.
 */
public class BillingModule extends AbstractModule {

        @Override
        protected void configure() {

     /*
      * This tells Guice that whenever it sees a dependency on a TransactionLog,
      * it should satisfy the dependency using a DatabaseTransactionLog.
      */
        bind(BillingService.class).to(RealBillingService.class);


    }
}
