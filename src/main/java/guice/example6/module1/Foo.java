package guice.example6.module1;

import com.google.inject.Inject;

public class Foo {
    private final Bar bar;

    @Inject
    public Foo(final Bar bar) {
        this.bar = bar;
    }

    public Bar getBar() {
        return bar;
    }
}