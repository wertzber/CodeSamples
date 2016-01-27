package subscription2.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription2.api.SubscriptionServerAccountUser;
import subscription2.api.SubscriptionServerBase;
import subscription2.exceptions.SubscriptionAlreadyExistsException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: ofirp
 * Date: 1/24/2016
 * Time: 2:24 PM
 */
public class SubscriptionServerAccountUserImpl<O,T,P> extends SubscriptionServerBaseImpl<O,T,P> implements SubscriptionServerAccountUser<O> {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionServerAccountUserImpl.class);
    private Map<String,Set<String>> accountSubscriptionSet;
    private Map<String,Set<String>> userSubscriptionSet;

    public SubscriptionServerAccountUserImpl() {
        this.accountSubscriptionSet = new ConcurrentHashMap<>(50);
        this.userSubscriptionSet = new ConcurrentHashMap<>(50);
    }

    @Override
    public String onSubscribe(O inSubscribeRequest, String accountId, String userId, Map<String, Object> params) throws SubscriptionAlreadyExistsException {
        String subscribeId = super.onSubscribe(inSubscribeRequest, params);
        accountSubscriptionSet.putIfAbsent(accountId, new HashSet<>());
        accountSubscriptionSet.get(accountId).add(subscribeId);
        userSubscriptionSet.putIfAbsent(userId, new HashSet<>());
        userSubscriptionSet.get(userId).add(subscribeId);
        logger.debug("Added user & account subscription Id {}, user {}, account {}", subscribeId, userId, accountId);
        return subscribeId;
    }

    @Override
    public void onUnSubscribe(O inUnSubscribeRequest, String accountId, String userId, Map<String, Object> params) {
        Set<String> subsIds = new HashSet<>(20);
        subsIds.addAll(accountSubscriptionSet.get(accountId));
        subsIds.addAll(userSubscriptionSet.get(userId));
        subsIds.stream().forEach(subsId -> super.onUnSubscribe(inUnSubscribeRequest, subsId));
        accountSubscriptionSet.remove(accountId);
        accountSubscriptionSet.remove(accountId);
        logger.debug("Removed subscription for accountId {} userId {}", accountId, userId);
    }

    @Override
    public void onUpdateSubscribe(O updateSubscribeRequest, String accountId, String userId, Map<String, Object> params) {
        // TODO implement
    }

    @Override
    public void onEvent(Object event, String accountId, String userId, Map<String, Object> params) {
        // TODO implement
    }
}
