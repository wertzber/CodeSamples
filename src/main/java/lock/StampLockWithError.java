package lock;

import java.util.concurrent.locks.StampedLock;

/**
 * Created by eladw on 1/19/2015.
 */
public class StampLockWithError {

    StampedLock lock = new StampedLock();

    public static void main(String args[]){
        StampLockWithError sampleLock = new StampLockWithError();

        Runnable task1 = () -> {
            System.out.println("Task #1 is running");
            sampleLock.testLock(false);
        };
        Runnable task2 = () -> {
            System.out.println("Task #2 is running");
            sampleLock.testLock(true);
        };
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runnable task3 = () -> {
            System.out.println("Task #3 is running");
            sampleLock.testLock(false);
        };
//        Runnable task3 = () -> {
//            System.out.println("Task #3 is running");
//            sampleLock.testLock(false);
//        };
//        Runnable task4 = () -> {
//            System.out.println("Task #4 is running");
//            sampleLock.testLock(false);
//        };
//        Runnable task5 = () -> {
//            System.out.println("Task #5 is running");
//            sampleLock.testLock(false);
//        };

        // start the thread
        new Thread(task1).start();
        new Thread(task2).start();
        new Thread(task3).start();

//        new Thread(task3).start();
//        new Thread(task4).start();
//        new Thread(task5).start();



        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void testLock(boolean isWrite) {
        // try the fast (non-blocking) lock first
        long stamp = 0L;
        try {
            stamp = lock.tryOptimisticRead();
            System.out.println("testLockStartStamp " + stamp);
            doSomeWork(isWrite);

            if (lock.validate(stamp)) {
                // Great! no contention, the work was successful
                System.out.println(Thread.currentThread().getName() + " Great!! light lock has done the work \n");
            } else {

                // another thread acquired a write lock while we were doing some work,
                // repeat the operation using the heavy (blocking) lock
                stamp = lock.readLock();
                System.out.println(Thread.currentThread().getName() + " New read lock created, stamp:" + stamp);

                doSomeWork();

            }

        } catch (Exception e){
            e.printStackTrace();
        }
        finally {

            System.out.println(Thread.currentThread().getName() + " Stamp in finally :" + stamp + "\n");
            // release the lock stamp
            boolean isSuccess = false;

            try{
                if(lock.isReadLocked()) lock.unlockRead(stamp);
            } catch(Exception e){
                System.out.println(Thread.currentThread().getName() + " Error in unlock " +  e + "\n");
            }

        }
    }

    private void doSomeWork() {
        System.out.println(Thread.currentThread().getName() + " Do something");

    }


    private void doSomeWork(boolean isWrite){
        if(isWrite){
            long stamp = lock.writeLock();  //blocking lock, returns a stamp
            try {
                System.out.println(Thread.currentThread().getName() + " Write...Stamp after lock:" + stamp); // this is a bad move, you’re letting the stamp escape
            }
            finally {
                lock.unlock(stamp);// release the lock in the same block - way better
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " Do something read" );

        }

    }


}
