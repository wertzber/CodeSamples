package subscription.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.SubscriptionServer;
import subscription.data.subscribe.SubscriptionData;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * Created by eladw on 1/21/2016.
 * T - The object for the predicate(ExtendConversation)
 * O - orig subscription msg(SubscribeExConversation)
 * P - predicate
 */
public class SubscriptionServerBaseImpl<O,T,P> implements SubscriptionServer<O,T,P> {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionServerBaseImpl.class);

    private Map<String,SubscriptionData<P, O>> subscriptionMap = new ConcurrentHashMap<>(50);
    private Map<String,Set<String>> userSubscriptionSet = new ConcurrentHashMap<>(50);
    private Map<String,Set<String>> accountSubscriptionSet = new ConcurrentHashMap<>(50);

    private Predicate<P> predicate;

    public SubscriptionServerBaseImpl(Predicate<P> predicate) {
        this.predicate = predicate;
    }

    public SubscriptionServerBaseImpl() {
    }

    @Override
    public String onSubscribe(O inSubscribeRequest, Map<String, Object> params) {

        String subscriptionId = UUID.randomUUID().toString();
        //TODO: check if already exists same subscribe?
        SubscriptionData<P,O> subsData =  new SubscriptionData<P, O>(
                predicate,
                inSubscribeRequest,
                subscriptionId);
        subscriptionMap.put(subscriptionId, subsData);
        logger.debug("Added subscription Id {}, subscription data {}", subscriptionId, subsData);
        return subscriptionId;
    }


        @Override
    public String onAccountSubscribe(O inSubscribeRequest, String accountId, Map<String, Object> params) {
        String subscribeId = onSubscribe(inSubscribeRequest, params);
        accountSubscriptionSet.putIfAbsent(accountId, new HashSet<>());
        accountSubscriptionSet.get(accountId).add(subscribeId);
        logger.debug("Added Acount subscription Id {}, account {}", subscribeId, accountId);
        return subscribeId;
    }

    @Override
    public String onUserSubscribe(O inSubscribeRequest, String userId, Map<String, Object> params) {
        String subscribeId = onSubscribe(inSubscribeRequest, params);
        userSubscriptionSet.putIfAbsent(userId, new HashSet<>());
        userSubscriptionSet.get(userId).add(subscribeId);
        logger.debug("Added user subscription Id {}, user {}", subscribeId, userId);
        return subscribeId;
    }

    @Override
    public String onAccountAndUserSubscribe(O inSubscribeRequest, String accountId, String userId, Map<String, Object> params) {
        String subscribeId = onSubscribe(inSubscribeRequest, params);
        accountSubscriptionSet.putIfAbsent(accountId, new HashSet<>());
        accountSubscriptionSet.get(accountId).add(subscribeId);
        userSubscriptionSet.putIfAbsent(userId, new HashSet<>());
        userSubscriptionSet.get(userId).add(subscribeId);
        logger.debug("Added user & account subscription Id {}, user {}, account {}", subscribeId, userId, accountId);
        return subscribeId;
    }


    @Override
    public void onUnSubscribe(O inUnSubscribeRequest, String subscribeId) {
        accountSubscriptionSet.remove(subscribeId);
        logger.debug("Remove subscription Id {}", subscribeId);
    }

    @Override
    public void onAccountUnSubscribe(O inUnSubscribeRequest, String account) {
        Set<String> subsIds = accountSubscriptionSet.get(account);
        subsIds.stream().forEach(subsId -> onUnSubscribe(inUnSubscribeRequest, subsId));
        accountSubscriptionSet.remove(account);
        logger.debug("Remove subscription Id {} account {}", account);

    }

    @Override
    public void onUserUnSubscribe(O inUnSubscribeRequest, String userId) {
        Set<String> subsIds = userSubscriptionSet.get(userId);
        subsIds.stream().forEach(subsId -> onUnSubscribe(inUnSubscribeRequest, subsId));
        accountSubscriptionSet.remove(userId);
        logger.debug("Remove subscription Id {} user {}", userId);
    }

    @Override
    public void onAccountAndUserUnSubscribe(O inUnSubscribeRequest, String account, String userId, Map<String, Object> params) {
        Set<String> subsIds = accountSubscriptionSet.get(account);
        subsIds.stream().forEach(subsId -> onUnSubscribe(inUnSubscribeRequest, subsId));
        accountSubscriptionSet.remove(account);
        subsIds.stream().forEach(subsId -> {
            if(subscriptionMap.get(subsId)!=null) onUnSubscribe(inUnSubscribeRequest, subsId);  //Double check
        });
        userSubscriptionSet.remove(userId);
        logger.debug("Remove subscription Id {} account {} user {}", account, userId);
    }

    @Override
    public void onUpdateSubscribe(O updateSubscribeRequest, String subscribeId, Map<String, Object> params) {
        logger.warn("Not supported yet");
    }

    @Override
    public void onEvent(Object event, Map<String, Object> params) {

    }

    @Override
    public void onAccountEvent(Object event, String account, Map<String, Object> params) {

    }

    @Override
    public void onUserEvent(Object event, String userId, Map<String, Object> params) {

    }

    @Override
    public void onAccountAndUserEvent(Object event,String accountId, String userId, Map<String, Object> params) {

    }


    @Override
    public Set<String> getAccountSubscriptionsIds(String account) {
        return accountSubscriptionSet.get(account);

    }

    @Override
    public SubscriptionData<P, O> getAccountSingleSubscription(String account, String subscribeId) {
        if(accountSubscriptionSet.get(account).contains(subscribeId)){
            return subscriptionMap.get(subscribeId);
        } else {
            logger.warn("Account {} doesn't have the subscribe Id {}",account, subscribeId );
            return null;
        }

    }

    @Override
    public Set<String> getUserSubscriptions(String userId) {
        return userSubscriptionSet.get(userId);

    }

    @Override
    public SubscriptionData<P, O> getUserSingleSubscription(String userId, String subscribeId) {
        if(userSubscriptionSet.get(userId).contains(subscribeId)){
            return subscriptionMap.get(subscribeId);
        } else {
            logger.warn("User {} doesn't have the subscribe Id {}", userId, subscribeId );
            return null;
        }
    }



}
