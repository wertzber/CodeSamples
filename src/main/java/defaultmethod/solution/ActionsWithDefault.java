package defaultmethod.solution;

/**
 * Created by eladw on 12/23/2014.
 */
public interface ActionsWithDefault {

    String read(String file);
    void write(String file, String text);
    default void printFile(String file){
        System.out.println("Print File test");
    }


}
