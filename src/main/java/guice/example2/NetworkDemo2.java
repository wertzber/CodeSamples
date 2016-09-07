package guice.example2;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Created by eladw on 9/5/2016.
 */
public class NetworkDemo2 {

    public String port;
    public String address;

    @Inject
    public NetworkDemo2(@Named("port") String port, @Named("address") String address) {
        this.port = port;
        this.address = address;
    }

    public void print( ){
        System.out.println("Port:" + port + " address:" + address );
    }

}
