package guice.example3;

/**
 * Created by eladw on 9/1/2016.
 */
public class StdoutDisplay implements Display {
    @Override
    public void display(String s) {
        System.out.println("Stdout ## " + s);
    }
}
