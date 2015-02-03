package optional;

import lambda.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by eladw on 2/3/2015.
 */
public class Optional2 {

    public static void main(String args[]) {
        List<Person> persons  = new ArrayList();
        persons.add(new Person("elad", 35));
        persons.add(new Person("elad2", 3));

        //good
        //Optional<Person> optionalPerson = Optional.ofNullable(persons.get(0));

        //empty
        //Optional<Person> optionalPerson = Optional.ofNullable(null);

        //exception
        Optional<Person> optionalPerson = Optional.ofNullable(persons.get(3));

        optionalPerson.ifPresent(p-> {
            System.out.println("Found person " + p);
        });

        //send all existing messages
        //Optional<List<Dto>> optionalList = Optional.ofNullable(store.getAllMessages(conversationId));
        //optionalList.ifPresent(list -> list.stream().forEach(msg -> consumer.accept(serializeFrom(msg))));
    }
 }


