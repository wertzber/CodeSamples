package lock;

import java.util.concurrent.locks.StampedLock;

/**
 * Created by eladw on 12/24/2014.
 */
public class SampleLock {

    public static void main(String args[]){
        SampleLock sampleLock = new SampleLock();
        sampleLock.testLock();
    }

    public void testLock(){
        StampedLock lock = new StampedLock();
        // try the fast (non-blocking) lock first
        long stamp = 0L;
        try {
            stamp = lock.tryOptimisticRead();

            doSomeWork();

            if (lock.validate(stamp)) {
                // Great! no contention, the work was successful
                System.out.println("Great!! light lock has done the work");
            } else {
                // another thread acquired a write lock while we were doing some work,
                // repeat the operation using the heavy (blocking) lock
                stamp = lock.readLock();

                doSomeWork();
                System.out.println("ooops!! Need tight lock for the work");

            }
        } finally {
            System.out.println("Stamp:" + stamp);
            // release the lock stamp
            if (stamp != 0L) {
                if(lock.isReadLocked()) lock.unlock(stamp);
            }
        }
    }

    private void doSomeWork(){
        System.out.println("Do something");
    }


}
