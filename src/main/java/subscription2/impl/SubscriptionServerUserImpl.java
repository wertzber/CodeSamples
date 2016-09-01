package subscription2.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription2.api.SubscriptionServerBase;
import subscription2.api.SubscriptionServerUser;
import subscription2.events.EventInfo;
import subscription2.exceptions.SubscriptionAlreadyExistsException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: ofirp
 * Date: 1/24/2016
 * Time: 1:49 PM
 */
public class SubscriptionServerUserImpl<O,P> implements SubscriptionServerUser<O,P> {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionServerUserImpl.class);
    private SubscriptionServerBase<O,P> serverBase;
    private Map<String,Set<String>> userSubscriptionSet;

    public SubscriptionServerUserImpl(SubscriptionServerBase<O,P> subscriptionServerBase) {
        if (subscriptionServerBase == null) throw new IllegalArgumentException("subscriptionServerBase must be supplied to the User Subscription Server");
        this.serverBase = subscriptionServerBase;
        this.userSubscriptionSet = new ConcurrentHashMap<>(50);
    }

    @Override
    public String onSubscribe(String clientId, O inSubscribeRequest, String userId, Map<String, Object> params) throws SubscriptionAlreadyExistsException {
        String subscribeId = serverBase.onSubscribe(clientId, inSubscribeRequest, params);
        userSubscriptionSet.putIfAbsent(userId, new HashSet<>());
        userSubscriptionSet.get(userId).add(subscribeId);
        logger.debug("Added user subscription Id {}, user {}", subscribeId, userId);
        return subscribeId;
    }

    @Override
    public void onUnSubscribe(String clientId, O inUnSubscribeRequest, String userId) {
        Set<String> subsIds = userSubscriptionSet.get(userId);
        subsIds.stream().forEach(subsId -> serverBase.onUnSubscribe(clientId, inUnSubscribeRequest, subsId));
        userSubscriptionSet.remove(userId);
        logger.debug("Remove subscription Id {} userId {}", userId);
    }

    @Override
    public void onUpdateSubscribe(String clientId, O updateSubscribeRequest, String subscriptionId, Map<String, Object> params) {
        // TODO implement
    }

    @Override
    public void onEvent(EventInfo eventInfo) {
        serverBase.onEvent(eventInfo);
    }

}
