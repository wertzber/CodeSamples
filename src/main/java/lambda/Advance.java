package lambda;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *
 * Created by eladw on 12/23/2014.
 */
public class Advance {

    List<Person> persons = null;

    //test
    public Advance(){
        persons = Arrays.asList(
                        new Person("Max", 18),
                        new Person("Peter", 23),
                        new Person("Peter2", 25),
                        new Person("Pamela", 23),
                        new Person("David", 12));
    }

    public static void main(String args[]){
        Advance advance = new Advance();
        advance.join();
    }

    /**
     * Collect is an extremely useful terminal operation to transform the elements of the stream into a different kind of result,
     * e.g. a List, Set or Map.
     * Collect accepts a Collector which consists of four different operations:
     * a supplier, an accumulator, a combiner and a finisher.
     * This sounds super complicated at first, but the good part is Java 8 supports various built-in collectors via the Collectors class.
     * So for the most common operations you don't have to implement a collector yourself.
     */
    public void collect(){
        List<Person> filtered =
                persons
                        .stream()
                        .filter(p -> p.name.startsWith("P"))
                        .collect(Collectors.toList());

        System.out.println(filtered);    // [Peter, Pamela]

        filtered.remove(0);
        System.out.println(persons);    // [Peter, Pamela]


//        Set<Person> filtered2 =
//                persons
//                        .stream()
//                        .filter(p -> p.name.startsWith("P"))
//                        .collect(Collectors.toSet());
//
//        System.out.println(filtered2);    // [
    }

    /**
     * Group results
     */
    public void group(){
        Map<Integer, List<Person>> personsByAge = persons
                .stream()
                .collect(Collectors.groupingBy(p -> p.age));

        personsByAge
                .forEach((age, p) -> System.out.format("age %s: %s\n", age, p));
    }

    /**
     * Average on collection
     */
    public void avg(){
        Double averageAge = persons
                .stream()
                .collect(Collectors.averagingInt(p -> p.age));

        System.out.println(averageAge);     // 19.0
    }

    /**
     * Stats new objects
     * IntSummaryStatistics{count=5, sum=99, min=12, average=19.800000, max=23}
     */
    public void stat(){
        IntSummaryStatistics ageSummary =
                persons
                        .stream()
                        .collect(Collectors.summarizingInt(p -> p.age));

        System.out.println(ageSummary);
    }

    /**
     * join string
     * In Germany Max and Peter and Peter2 and Pamela are of legal age.
     */
    public void join(){
        String phrase = persons
                .stream()
                .filter(p -> p.age >= 18)
                .map(p -> p.name)
                .collect(Collectors.joining(" and ", "In Germany ", " are of legal age."));

        System.out.println(phrase);
    }

    /**
     * from list to map(key,value,merger)
     *{18=Max, 23=Peter;Pamela, 25=Peter2, 12=David}
     */
    public void toMap(){
        Map<Integer, String> map = persons
                .stream()
                .collect(Collectors.toMap(
                        p -> p.age, //key
                        p -> p.name,    //value
                        (name1, name2) -> name1 + ";" + name2));    //merger

        System.out.println(map);
    }

   public void newCollector(){
       Collector<Person, StringJoiner, String> personNameCollector =
               Collector.of(
                       () -> new StringJoiner(" |--|"),        // supplier
                       (j, p) -> j.add(p.name + p.age),        // accumulator
                       (j1, j2) -> j1.merge(j2),               // combiner
                       StringJoiner::toString);                // finisher

       String names = persons
               .stream()
               .collect(personNameCollector);

       System.out.println(names);  // MAX | PETER | PAMELA | DAVID
   }



}

