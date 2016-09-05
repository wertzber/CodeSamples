package guice.example5;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import guice.example5.pojo.BillingService;
import guice.example5.pojo.RealBillingService;
import guice.example5.pojo.Report;

import java.util.Date;

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
        bind(BillingService.class).to(RealBillingService.class).in(Singleton.class);


       }
    @Provides @Singleton
    Report provideReport() {
        Report report = new Report("elad", new Date(), 1234l);
        return report;
    }

}
