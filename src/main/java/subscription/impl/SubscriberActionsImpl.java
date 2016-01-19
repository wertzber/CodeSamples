package subscription.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.SubscriptionActions;
import subscription.data.subscribe.SubscriptionData;
import subscription.factory.PredicateFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by eladw on 1/4/2016.
 * T - The object for the predicate(ExtendConversation)
 * O - orig subscription msg(SubscribeExConversation)
 * P - predicate
 */
public class SubscriberActionsImpl<T, O> implements SubscriptionActions<T, O> {

    private static final Logger logger = LoggerFactory.getLogger(SubscriberActionsImpl.class);

    //account subs use the account as main key, and subsId as secondary key.
    private Map<String, Map<String, SubscriptionData<T, O>>> accountSubscriptionMap = new ConcurrentHashMap<>(50);
    //Map user to all its subscriptions
    private Map<String, List<String>> userSubscriptionMap = new ConcurrentHashMap<>(50);

    @Override
    public boolean removeAccount(String account) {
        accountSubscriptionMap.remove(account);
        return true;
    }

    @Override
    public boolean removeSubscriber(String account, String subscribeId) {
        Map<String, SubscriptionData<T, O>> specificAccountSubscriptions = accountSubscriptionMap.get(account);
        accountSubscriptionMap.remove(subscribeId);

        return true;
    }

    @Override
    public boolean removeSubscriberUser(String account, String userId) {
        List<String> userIds = userSubscriptionMap.get(userId);

        Map<String, SubscriptionData<T, O>> accountMap = accountSubscriptionMap.get(account);
        for(String id : userIds){
            accountMap.remove(id);
            logger.debug("removed Subs id: {} ", id);
        }
        userSubscriptionMap.remove(userId);
        return true;
    }

    @Override
    public String addSubscriber(String account, String userId, Predicate<T> predicate,
                                 O subscribeRequest) {
        String subscriptionId = null;
        //if userId has no query request yet. that is he is not registered to anything yet so create map fo user
        // even if the keys are different should definitely use ConcurrentHashMap (Suppose two threads each need to expand the internal table at the same time)
        accountSubscriptionMap.putIfAbsent(account, new ConcurrentHashMap<>(1));

        //add subscription to given user and the map for the user is guaranteed to be created
        subscriptionId = UUID.randomUUID().toString();
        final String tempid = subscriptionId;
        accountSubscriptionMap.compute(account, (subscId, innerMap) -> {
            createSubscribeData(subscId, subscribeRequest, tempid, predicate, innerMap);
            return innerMap;
        });
        logger.debug("user  id {} added subscription  with account =  {} and subscribe Id {}",userId, account, subscriptionId);
        userSubscriptionMap.putIfAbsent(userId, new ArrayList<>());
        userSubscriptionMap.get(userId).add(subscriptionId);
        return subscriptionId;
    }

    @Override
    public Map<String, SubscriptionData<T, O>> getAccountSubscriptions(String account) {
        return accountSubscriptionMap.get(account);
    }

    @Override
    public SubscriptionData<T, O> getSubscription(String account, String subscribeId) {
        return (SubscriptionData<T, O>) accountSubscriptionMap.get(account).entrySet()
                .stream()
                .filter(id -> id.equals(subscribeId))
                .findFirst().get();
    }

    @Override
    public List<SubscriptionData<T, O>> getUserSubscriptions(String account, String userId) {

        List<String> subsIds = userSubscriptionMap.get(userId);
        return subsIds.stream().map(id-> accountSubscriptionMap.get(account).get(id)).collect(Collectors.toList());
    }


    private void createSubscribeData(String subscId, O subscription, String subscriptionId, Predicate predicate,
                                     Map<String,SubscriptionData<T, O>> innerMap) {

        SubscriptionData<T, O> subscriptionMapEntry = innerMap.get(subscId);
        //Predicate<P> predicate = PredicateFactory.getPredicate(predicateClass, subscription);

        innerMap.put(subscriptionId, new SubscriptionData<T, O>(
                predicate,
                subscription,
                subscriptionId));

    }

}
