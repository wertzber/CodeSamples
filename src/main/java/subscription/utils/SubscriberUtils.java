package subscription.utils;

import java.util.UUID;

/**
 * Created by eladw on 1/6/2016.
 */
public final class SubscriberUtils {

    private SubscriberUtils(){

    }

    public static String generateSubscriberId(){
        return UUID.randomUUID().toString();
    }


}
