package subscription2.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription2.api.SubscriptionServerUser;
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
public class SubscriptionServerUserImpl<O,T,P> extends SubscriptionServerBaseImpl<O,T,P> implements SubscriptionServerUser<O> {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionServerUserImpl.class);
    private SubscriptionServerBaseImpl<O,T,P> serverBase;
    private Map<String,Set<String>> userSubscriptionSet;

    public SubscriptionServerUserImpl(SubscriptionServerBaseImpl<O, T, P> serverBase) {
        this.serverBase = serverBase;
        this.userSubscriptionSet = new ConcurrentHashMap<>(50);
    }

    @Override
    public String onSubscribe(O inSubscribeRequest, String userId, Map<String, Object> params) throws SubscriptionAlreadyExistsException {
        String subscribeId = serverBase.onSubscribe(inSubscribeRequest, params);
        userSubscriptionSet.putIfAbsent(userId, new HashSet<>());
        userSubscriptionSet.get(userId).add(subscribeId);
        logger.debug("Added user subscription Id {}, user {}", subscribeId, userId);
        return subscribeId;
    }

    @Override
    public void onUnSubscribe(O inUnSubscribeRequest, String userId) {
        Set<String> subsIds = userSubscriptionSet.get(userId);
        subsIds.stream().forEach(subsId -> serverBase.onUnSubscribe(inUnSubscribeRequest, subsId));
        userSubscriptionSet.remove(userId);
        logger.debug("Remove subscription Id {} userId {}", userId);
    }

    @Override
    public void onUpdateSubscribe(O updateSubscribeRequest, String subscriptionId, Map<String, Object> params) {
        // TODO implement
    }

    @Override
    public void onEvent(Object event, String userId, Map<String, Object> params) {
        // TODO implement
    }

}
