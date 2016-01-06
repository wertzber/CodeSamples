package subscription.impl;

import com.liveperson.api.server.Remote;
import subscription.api.SubscriptionSender;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eladw on 1/6/2016.
 * All Sender activities and Remote connections are managed here.
 */
public class SubscriberSenderImpl implements SubscriptionSender {

//    private final Map<String, Remote> remoteSubscriptionMap = new ConcurrentHashMap<>(50);
//
//
//    public Remote getRemote(String subscribeId){
//        return remoteSubscriptionMap.get(subscribeId);
//    }
//
//    public boolean updateRemote(String subscribeId,Remote remote){
//        remoteSubscriptionMap.compute(subscribeId, (k,v)-> remote);
//        return true;
//    }
//
//    public boolean removeRemote(String subscribeId){
//        remoteSubscriptionMap.remove(subscribeId);
//        return true;
//    }
//
//    public Map<String, Remote> getRemoteSubscriptionMap() {
//        return remoteSubscriptionMap;
//    }
}
