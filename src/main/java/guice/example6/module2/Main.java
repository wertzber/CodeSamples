package guice.example6.module2;

import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.example6.module1.Foo;

/**
 * Created by eladw on 9/5/2016.
 */
public class Main {

    public static void main(final String[] args) {
        final Injector injector = Guice.createInjector(new ParentModule());
        final Foo foo = injector.getInstance(Foo.class);
        System.out.println(foo.getBar());
    }
}
