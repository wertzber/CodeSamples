package guice.example6.module2;

import com.google.inject.AbstractModule;
import guice.example6.module1.ChildModule;
import guice.example6.module1.Foo;

/**
 * Created by eladw on 9/5/2016.
 */
public class ParentModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new ChildModule());
        bind(Foo.class);
    }
}