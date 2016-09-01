package guice.service;

import com.google.inject.AbstractModule;
import guice.pojo.BillingService;
import guice.pojo.RealBillingService;

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
