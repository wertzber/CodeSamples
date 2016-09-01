//package subscription2.impl;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import subscription2.api.SubscriptionServerAccount;
//import subscription2.exceptions.SubscriptionAlreadyExistsException;
//
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Created with IntelliJ IDEA.
// * User: ofirp
// * Date: 1/24/2016
// * Time: 12:07 PM
// */
//public class SubscriptionServerAccountImpl<O,T,P> extends SubscriptionServerBaseImpl<O,T,P> implements SubscriptionServerAccount<O> {
//
//    private static final Logger logger = LoggerFactory.getLogger(SubscriptionServerAccountImpl.class);
//    private Map<String,Set<String>> accountSubscriptionSet;
//
//    public SubscriptionServerAccountImpl(SubscriptionServerBaseImpl<O, T, P> serverBase) {
//        this.accountSubscriptionSet = new ConcurrentHashMap<>(50);
//    }
//
//    @Override
//    public String onSubscribe(O inSubscribeRequest, String accountId, Map<String, Object> params) throws SubscriptionAlreadyExistsException {
//        String subscribeId = super.onSubscribe(inSubscribeRequest, params);
//        accountSubscriptionSet.putIfAbsent(accountId, new HashSet<>());
//        accountSubscriptionSet.get(accountId).add(subscribeId);
//        logger.debug("Added account subscription Id {}, account {}", subscribeId, accountId);
//        return subscribeId;
//    }
//
//    @Override
//    public void onUnSubscribe(O inUnSubscribeRequest, String accountId) {
//        Set<String> subsIds = accountSubscriptionSet.get(accountId);
//        subsIds.stream().forEach(subsId -> super.onUnSubscribe(inUnSubscribeRequest, subsId));
//        accountSubscriptionSet.remove(accountId);
//        logger.debug("Remove subscription Id {} accountId {}", accountId);
//    }
//
//    @Override
//    public void onUpdateSubscribe(O updateSubscribeRequest, String subscriptionId, Map<String, Object> params) {
//        // TODO implement
//    }
//
//    @Override
//    public void onEvent(Object event, String accountId, Map<String, Object> params) {
//        // TODO implement
//    }
//}
