package subscription.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.SubscriptionActions;
import subscription.data.aam.AamPredicate;
import subscription.data.subscribe.SubscriptionData;
import subscription.factory.PredicateFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * Created by eladw on 1/4/2016.
 * T - The object for the predicate(ExtendConversation)
 * O - orig subscription msg(SubscribeExConversation)
 * P - predicate
 */
public class SubscriberActionsImpl<T, O, P> implements SubscriptionActions<P, O> {

    private static final Logger logger = LoggerFactory.getLogger(SubscriberActionsImpl.class);

    //account subs use the account as main key, and subsId as secondary key.
    private final Map<String, Map<String, SubscriptionData<P, O>>> accountSubscriptionMap = new ConcurrentHashMap<>(50);
    //Map user to all its subscriptions
    private final Map<String, List<String>> userSubscriptionMap = new ConcurrentHashMap<>(50);

    private Class<P> predicateClass;

    public SubscriberActionsImpl(Class<P> predicateClass) {
        this.predicateClass = predicateClass;
    }



    @Override
    public boolean removeAccount(String account) {
        accountSubscriptionMap.remove(account);
        return true;
    }

    @Override
    public boolean removeSubscriber(String account, String subscribeId) {
        Map<String, SubscriptionData<P, O>> specificAccountSubscriptions = accountSubscriptionMap.get(account);
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
        logger.debug("user  id {} added subscription  with account =  {} and subscribe Id {}",userId, account, subscriptionId);
        userSubscriptionMap.putIfAbsent(userId, new ArrayList<>());
        userSubscriptionMap.get(userId).add(subscriptionId);
        return subscriptionId;
    }

    @Override
    public Map<String, SubscriptionData<P, O>> getAccountSubscriptions(String account) {
        return accountSubscriptionMap.get(account);
    }

    @Override
    public SubscriptionData<P,O> getSubscription(String account, String subscribeId) {
        return (SubscriptionData<P, O>) accountSubscriptionMap.get(account).entrySet()
                .stream()
                .filter(id -> id.equals(subscribeId))
                .findFirst().get();
    }

    @Override
    public List<String> getUserSubscriptions(String account, String userId) {
        return userSubscriptionMap.get(userId);
    }


    private void createSubscribeData(String subscId, O subscription, String subscriptionId,
                                     Map<String,SubscriptionData<P, O>> innerMap) {

        SubscriptionData<P, O> subscriptionMapEntry = innerMap.get(subscId);
        Predicate<P> predicate = PredicateFactory.getPredicate(predicateClass, subscription);
        innerMap.put(subscriptionId, new SubscriptionData<P, O>(
                predicate,
                subscription,
                subscriptionId));

    }

}
