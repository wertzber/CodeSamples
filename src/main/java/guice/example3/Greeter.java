package guice.example3;

import com.google.inject.Inject;

/**
 * Created by eladw on 9/1/2016.
 */
public class Greeter {

    @Inject @Screen Display display;

    public void sayHello(){
        display.display("elad");
    }
}
