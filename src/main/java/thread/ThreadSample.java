package thread;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by eladw on 12/25/2014.
 */
public class ThreadSample {

    public static void main(String args[]) {
        ThreadSample threadSample = new ThreadSample();
        threadSample.threadTest2();


    }

    public static int longOperation()  {
        System.out.println("Running on thread #"
                + Thread.currentThread().getId());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 42;
    }

//    public static int longOperation(int num) throws Exception {
//        if(num ==10) throw new Exception("fail");
//        System.out.println("Running on thread #"
//                + Thread.currentThread().getId());
//        return 42;
//    }

    public void threadTest(){
        Thread[] threads = {
                // Pass a lambda to a thread
                new Thread(() -> {
                    longOperation();
                }),
                // Pass a method reference to a thread
                new Thread(ThreadSample::longOperation)
        };

        // Start all threads
        Arrays.stream(threads).forEach(Thread::start);

        // Join all threads
        Arrays.stream(threads).forEach(t -> {
            try { t.join(); }
            catch (InterruptedException ignore) {}
        });

        System.out.println("done !!");

    }

    public void threadTest2(){
        ExecutorService service = Executors
                .newFixedThreadPool(5);

        Future[] answers = {
                service.submit(() -> longOperation()),
                service.submit(ThreadSample::longOperation)
        };

        Arrays.stream(answers).forEach(f -> System.out.println("finish "));
    }



}
