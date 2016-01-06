package subscription.impl;

import com.liveperson.api.ams.aam.SubscribeExConversations;
import com.liveperson.api.server.Remote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.SubscriberActions;
import subscription.data.aam.AamPredicate;
import subscription.data.aam.ExtendedConversation;
import subscription.factory.PredicateFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * Created by eladw on 1/4/2016.
 * T - The object for the predicate
 * O - orig subscription msg
 */
public class SubscriberActionsImpl<T, O> implements SubscriberActions<T, O> {

    private static final Logger logger = LoggerFactory.getLogger(SubscriberActionsImpl.class);

    //account subs use the accopunt as main key, and subsId as secondary key.
    private final Map<String, Map<String, SubscriptionData<T, O>>> accountSubscriptionMap = new ConcurrentHashMap<>(50);
    //Map user to all its subscriptions
    private final Map<String, List<String>> userSubscriptionMap = new ConcurrentHashMap<>(50);

    private Class<T> predicateClass;

    public SubscriberActionsImpl(Class<T> predicateClass) {
        this.predicateClass = predicateClass;
    }

    @Override
    public boolean removeAccount(String account) {
        accountSubscriptionMap.remove(account);
        return true;
    }

    @Override
    public boolean removeSubscriber(String account, String subscribeId) {
        Map<String, SubscriptionData<T, O>> specificAccountSubscriptions = accountSubscriptionMap.get(account);
        specificAccountSubscriptions.remove(subscribeId);
        return true;
    }

    @Override
    public boolean removeSubscriberUser(String account, String userId) {
        List<String> specificAccountSubscriptions = userSubscriptionMap.get(userId);
        specificAccountSubscriptions.removeIf(key -> key.equals(userId));
        return true;
    }

    @Override
    public String addSubscriber(String account, String userId,
                                 O subscribeRequest) {
        String subscriptionId = null;
        //if userId has no query request yet. that is he is not registered to anything yet so create map fo user
        // even if the keys are different should definitely use ConcurrentHashMap (Suppose two threads each need to expand the internal table at the same time)
        accountSubscriptionMap.putIfAbsent(account, new ConcurrentHashMap<>(1));

        //add subscription to given user and the map for the user is guaranteed to be created
        subscriptionId = UUID.randomUUID().toString();
        final String tempid = subscriptionId;
        accountSubscriptionMap.compute(account, (subscId, innerMap) -> {
            createSubscribeData(subscId, subscribeRequest, tempid, innerMap);
            return innerMap;
        });
        logger.debug("user  id {} added subscription  with account =  {}", account, subscriptionId);
        userSubscriptionMap.putIfAbsent(userId, new ArrayList<>());
        userSubscriptionMap.get(userId).add(subscriptionId);
        return subscriptionId;
    }

    @Override
    public Map<String, SubscriptionData<T, O>> getAccountSubscriptions(String account) {
        return accountSubscriptionMap.get(account);
    }

    @Override
    public T getSubscription(String account, String subscribeId) {
        return (T) accountSubscriptionMap.get(account).entrySet()
                .stream()
                .filter(id -> id.equals(subscribeId))
                .findFirst().get();
    }

    @Override
    public List<String> getUserSubscriptions(String account, String userId) {
        return userSubscriptionMap.get(userId);
    }


    private void createSubscribeData(String subscId, O subscription, String subscriptionId,
                                     Map<String,SubscriptionData<T, O>> innerMap) {

        SubscriptionData<T, O> subScriptionMapEntry = innerMap.get(subscId);
        Predicate<T> predicate = PredicateFactory.getPredicate(predicateClass, subscription);
        innerMap.put(subscriptionId, new SubscriptionData<T, O>(
                predicate,
                subscription,
                subscriptionId));

    }
//
//    @Override
//    public boolean removeSubscriber(String primaryKey, String secondaryKey) {
//        boolean retVal = false;
//        Optional<Map.Entry<String, Map<String, SubscriptionData<T,O>>>> queryBySubscriptionId
//                = getBySubscriptionId(primaryKey);
//        if (queryBySubscriptionId.isPresent()) {
//            Map.Entry<String, Map<String, SubscriptionData<T,O>>> entry = queryBySubscriptionId.get();
//            Map<String, SubscriptionData<T,O>> dataMap = subscriptionMap.get(entry.getKey());
//            if(entry.getKey().equals(primaryKey)) {
//                dataMap.remove(secondaryKey);
//                logger.debug("subscription primaryKey {}, secondaryKey {} is removed", primaryKey,secondaryKey);
//                retVal = true;
//            }
//        }
//        return retVal;
//    }
//
//    @Override
//    public boolean addSubscriber(String primaryKey, String secondary, T subscription) {
//        //if userId has no query request yet. that is he is not registered to anything yet so create map fo user
//        // even if the keys are different should definitely use ConcurrentHashMap (Suppose two threads each need to expand the internal table at the same time)
//        if (!subscriptionMap.containsKey(primaryKey))
//            subscriptionMap.putIfAbsent(primaryKey, new ConcurrentHashMap<>(1));
//        //add subscription to given user and the map for the user is guaranteed to be created
//        final String subscriptionId = UUID.randomUUID().toString();
//        subscriptionMap.compute(primaryKey, (subscId, innerMap) -> {
//            createSubscribeData(subscId, subscription, subscriptionId, innerMap);
//            return innerMap;
//        });
//        logger.debug("user  id {} added subscription  with primaryKey =  {}", primaryKey, subscriptionId);
//        return true;
//    }
//
//
//
//    @Override
//    public T getSubscriberId(String primarykey) {
//        return null;
//    }
//
//    @Override
//    public T getSubscriberId(String primarykey, String secondaryKey) {
//        return null;
//    }
//
//
//    /**
//     * primarykey->multiple subscribers, so run over all users and for each filter
//     * if subscribe id equals
//     * @param subscriptionId
//     * @return
//     */
//    private Optional<Map.Entry<String, Map<String, SubscriptionData<T,O>>>> getBySubscriptionId(String subscriptionId) {
//        return subscriptionMap.entrySet()
//                .stream()
//                .filter(e -> e.getValue().containsKey(subscriptionId))
//                .findFirst();
//    }
//


}
