package optional;

import lambda.Person;

import java.util.Optional;

/**
 * Created by eladw on 12/24/2014.
 */
public class SampleOptional {

    public static void main(String args[]) {
        SampleOptional sampleLock = new SampleOptional();
        sampleLock.testOptional();
    }

    public void testOptional(){

        Person p = new Person("elad", 35);
        Optional<Person> optionalPerson = p.getTest("null");
        optionalPerson.ifPresent(System.out::println);




    }


}
