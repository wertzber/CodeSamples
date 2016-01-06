package lambda;

import java.util.function.Function;

/**
 * Created by eladw on 4/29/2015.
 */
public class DebugTest {

    public static void main(String args[]){
        int start = 3;
//        Function<Integer, Integer> test1 = ((input)->{
//            int start = 5;
//            return input + start;
//        });
        f(j->start +j);
        //f(test1);

    }

    public static void f(Function<Integer, Integer> func){
        System.out.print(func.apply(5));
    }

}
