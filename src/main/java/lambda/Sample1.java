package lambda;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by eladw on 12/22/2014.
 */
public class Sample1 {

    public static void main(String args[]){
        Sample1 sample1 = new Sample1();
        sample1.streamSample();
        //sample1.streamSample4();
        //sample1.range();
        //sample1.streamToObj();
        //sample1.sort();
    }

    /**
     * Sample list, run over and print
     */
    public void listSample(){
        String[] atp = {"Rafael Nadal", "Novak Djokovic", "Stanislas Wawrinka", "David Ferrer", "Roger Federer", "Andy Murray", "Tomas Berdych", "Juan Martin Del Potro"};
        List<String> players =  Arrays.asList(atp);

        // Old looping
//        for (String player : players) {
//            System.out.print(player + "; ");
//        }

        // Using lambda expression and functional operations
        players.forEach( player -> System.out.print(player + "; "));

        // Using double colon operator in Java 8
        players.forEach(System.out::println);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sample list, use stream, change value and print
     */
    public void listSample2(){
        List<String> names = new ArrayList<>();
        names.add("elad0");
        names.add("elad1");
        names.add("elad2");
        names.add("elad3");

        //temp print
        names.stream().map(x-> x + "@@@").forEach(x->System.out.println("2: " + x));

        //print source list
        names.forEach((x)->System.out.println(x));

    }

    /**
     * Stream functions:
     * filter
     * map
     * sorted
     *
     */
    public void streamSample(){
        List<String> myList =
                Arrays.asList("a1", "a2", "b1", "c2", "c1");

        myList
                .stream()
                .filter(s ->{
                    System.out.print("eeee");
                    return s.startsWith("c");
                })
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);

        myList.forEach((x) -> System.out.println("2:" + x));
    }

    /**
     * isPresent
     */
    public void streamSample2() {
        Arrays.asList("a1", "a2", "a3")
                .stream()
                .findFirst()
                .ifPresent(System.out::println);  // a1
    }

    /**
     * Count + Stream of
     */
    public void streamSample3(){
        System.out.println(Arrays.asList("a1", "a2", "a3")
                .stream().count());


        Stream.of("a1", "a2", "a3")
                .findFirst()
                .ifPresent(System.out::println);  // a1

    }

    /**
     * Besides regular object streams Java 8 ships with special kinds of streams for working with the primitive data types
     * int, long and double. As you might have guessed it's IntStream, LongStream and DoubleStream.
     */
    public void range(){
        IntStream.range(1, 4)
                .forEach(System.out::println);
    }

    /**
     *
     */
    public void streamSample4(){
        Arrays.stream(new int[] {1, 2, 3})
                .map(n -> 2 * n + 1)
                .average()
                .ifPresent(System.out::println);  // 5.0
    }

    /**
     * Primitive streams can be transformed to object streams via mapToObj():
     */
    public void streamToObj(){
        IntStream.range(1, 4)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);
    }

    /**
     * Stop after first match
     * // map:      d2
     // anyMatch: D2
     // map:      a2
     // anyMatch: A2
     */
    public void streamAnyMatch(){
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return s.startsWith("A");
                });
    }

    /**
     * Each element moves along the chain vertically.
     * Sample output:
     * filter: d2
       filter: b2
       forEach: b2
       filter: a1
       filter: b3
       forEach: b3
       filter: c
     */
    public void streamOrder(){
        Stream.of("d2", "b2", "a1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("b");
                })
                .forEach(s -> System.out.println("forEach: " + s));
    }

    /**
     * Filter save steps
     */
    public void order2(){
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })

                .forEach(s -> System.out.println("forEach: " + s));
    }

    /**
     * Java 8 streams cannot be reused. As soon as you call any terminal operation the stream is closed:
     */
    public void reuseStream(){Stream<String> stream =
            Stream.of("d2", "a2", "b1", "b3", "c")
                    .filter(s -> s.startsWith("a"));

        stream.anyMatch(s -> true);    // ok
        stream.noneMatch(s -> true);   // exception
    }

    /**
     * Solve reuse stream
     * we could create a stream supplier to construct a new stream with all intermediate operations already set up:
     * Each call to get() constructs a new stream on which we are save to call the desired terminal operation.
     */
    public void reuseStreamOk(){
        Supplier<Stream<String>> streamSupplier =
                () -> Stream.of("d2", "a2", "b1", "b3", "c")
                        .filter(s -> s.startsWith("a"));

        streamSupplier.get().anyMatch(s -> true);   // ok
        streamSupplier.get().noneMatch(s -> true);  // ok
    }


    /**
     * [Person{name='Arun', age=22}, Person{name='Rahul', age=44}, Person{name='Rajesh', age=33}, Person{name='Virat', age=13}]

     */
    public void sort(){
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Virat", 13));
        personList.add(new Person("Arun", 22));
        personList.add(new Person("Rajesh",33));
        personList.add(new Person("Rahul", 44));


        //Sorting using Anonymous Inner class.
        Collections.sort(personList, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                return p1.name.compareTo(p2.name);
            }
        });



        //Anonymous Inner class replaced with Lambda expression.
        Collections.sort(personList, (Person p1, Person p2) -> p1.name.compareTo(p2.name));

        //Using sort method in List.
        personList.sort((p1, p2) -> p1.name.compareTo(p2.name));

        System.out.println(personList);


    }





}
