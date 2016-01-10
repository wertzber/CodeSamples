package subscription.test;

/**
 * Created by eladw on 1/9/2016.
 */
public class SubScribeTestResult {

    String name;
    long insertTime;
    String data;

    public SubScribeTestResult(String name, long insertTime, String data) {
        this.name = name;
        this.insertTime = insertTime;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public long getInsertTime() {
        return insertTime;
    }

    public String getData() {
        return data;
    }
}
