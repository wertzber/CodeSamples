package guice.example6.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import guice.example6.module1.ChildModule;
import guice.example6.module2.ParentModule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by eladw on 9/5/2016.
 */
public class TestModule {


    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new AbstractModule() {

            @Override
            protected void configure() {
                install(new ChildModule());
                bind(Foo.class).in(Scopes.SINGLETON);
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        injector = null;
    }

    @Test
    public void test() {
        final Injector injector = Guice.createInjector(new ParentModule());
        Foo foo = injector.getInstance(Foo.class);
        System.out.println(foo.getBar());

    }
}
