package guice.example1;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import guice.example1.pojo.BillingService;
import guice.example1.pojo.FakeBillingService;
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
                bind(BillingService.class).to(FakeBillingService.class).in(Scopes.SINGLETON);
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
