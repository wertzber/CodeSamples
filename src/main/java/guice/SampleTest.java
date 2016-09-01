package guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.pojo.BillingService;
import guice.pojo.FakeBillingService;
import guice.service.BillingModule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by eladw on 9/1/2016.
 */
public class SampleTest {

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new AbstractModule() {

            @Override
            protected void configure() {
                bind(BillingService.class).to(FakeBillingService.class);
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        injector = null;
    }

    @Test
    public void test() {
        BillingService billingService = injector.getInstance(BillingService.class);
        billingService.chargeOrder(100);
    }
}
