package guice.example6.Test;

import com.google.inject.Inject;
import guice.example6.module1.Bar;

/**
 * Created by eladw on 9/5/2016.
 */
public class Foo {

    private final Bar bar;

    @Inject
    public Foo(final Bar bar) {
        this.bar = bar;
    }

    public Bar getBar() {
        return new Bar("this is mock foo");
    }
}
