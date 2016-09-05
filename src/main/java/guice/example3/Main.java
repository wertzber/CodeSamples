package guice.example3;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by eladw on 9/1/2016.
 */
public class Main {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ExampleModule());
        Greeter greeter = injector.getInstance(Greeter.class);
        greeter.sayHello();

    }
}
