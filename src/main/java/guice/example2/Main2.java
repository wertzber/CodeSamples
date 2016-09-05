package guice.example2;

import com.google.inject.CreationException;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;


/**
 * Created by eladw on 8/31/2016.
 */
public class Main2 {

    public static Injector injector;




    public static void main(String[] args) {

        try {
              injector  = Guice.createInjector(new BillingModule());
              injector.getInstance(NetworkDemo.class).print();
        } catch (CreationException createE) {
            System.out.println("Create exception " + createE);
            createE.printStackTrace();
        } catch (Exception e){
            System.out.println("exception " + e);
            e.printStackTrace();
        } finally {

        }
    }



}
