package guice.example2;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Created by eladw on 9/5/2016.
 */
public class NetworkDemo {

    public int port;
    public String address;

    @Inject
    public NetworkDemo(@Named("port") int port,@Named("address") String address) {
        this.port = port;
        this.address = address;
    }

    public void print( ){
        System.out.println("Port:" + port + " address:" + address );
    }

}
