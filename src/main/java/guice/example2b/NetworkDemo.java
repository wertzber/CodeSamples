package guice.example2b;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import guice.example2b.annotations.Port;

/**
 * Created by eladw on 9/5/2016.
 */
public class NetworkDemo {

    public int port;
    public String address;

    @Inject
    public NetworkDemo(@Port int port,@Named("address") String address) {
        this.port = port;
        this.address = address;
    }

    public void print( ){
        System.out.println("Port:" + port + " address:" + address );
    }

}
