package guice.example6.module1;

import com.google.inject.AbstractModule;

/**
 * Created by eladw on 9/5/2016.
 */
public class ChildModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Bar.class).toInstance(new Bar("from ChildModule"));
    }
}