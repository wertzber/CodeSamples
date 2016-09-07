package guice.example2b;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import guice.example1.pojo.BillingService;
import guice.example1.pojo.RealBillingService;
import guice.example2b.annotations.Address;
import guice.example2b.annotations.Port;
import org.skife.jdbi.v2.sqlobject.Bind;

/**
 * Created by eladw on 8/31/2016.
 */
public class BillingModule extends AbstractModule {

        @Override
        protected void configure() {


            bindConstant().annotatedWith(Port.class).to(8000);
            bindConstant().annotatedWith(Address.class).to("192.168.12.10");

        }
}
