package lambda;

import java.util.Comparator;
import java.util.Optional;

/**
 * Created by eladw on 12/23/2014.
 */
public class Person implements Comparator{
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Optional<Person> getTest(String name){
        if(name=="null")
            return null;
        return Optional.of(this);
    }

    @Override
    public int compare(Object o1, Object o2) {
        int p1 = ((Person)o1).age;
        int p2 = ((Person)o2).age;
        return p1 - p2;
    }
}