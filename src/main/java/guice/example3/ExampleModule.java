package guice.example3;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Created by eladw on 9/1/2016.
 */
public class ExampleModule extends AbstractModule {

    @Override
    public void configure() {
        bind(Display.class).annotatedWith(Screen.class).to(ScreenDisplay.class);
        bind(Display.class).annotatedWith(Std.class).to(StdoutDisplay.class);

    }
}
