package defaultmethod.solution;

/**
 * Created by eladw on 12/23/2014.
 * Due the default method all inherit objects are still compiled
 * You can override the default
 */
public class Elad2Actions implements ActionsWithDefault {
    @Override
    public String read(String file) {
        printFile(file);
        return null;
    }

    @Override
    public void write(String file, String text) {

    }


}
