package compute;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eladw on 1/6/2016.
 */
public class ComputeTest {

    public static void main(String[] args) {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);

        map.compute("A", (k, v) -> 3);
        System.out.print(map.get("A"));
    }

}
