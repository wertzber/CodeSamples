package subscription2.impl;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.SubscriptionSender;
import subscription2.api.DataSupplier;
import subscription2.api.SubscriptionServerBase;
import subscription2.events.EventInfo;
import subscription2.exceptions.SubscriptionAlreadyExistsException;
import subscription2.predicate.PredicateSupplier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * Created with IntelliJ IDEA.
 * User: ofirp
 * Date: 2/2/2016
 * Time: 11:17 AM
 */
public class SubscriptionServerBaseImplTest {

    @Test
    public void subscriptionServerTest() throws SubscriptionAlreadyExistsException {

        Map<String,List<Integer>> results = new ConcurrentHashMap<>();
        final String oddNumbersSubscriber = "oddNumbersSubscriber";
        final String evenNumbersSubscriber = "evenNumbersSubscriber";
        results.put(oddNumbersSubscriber, new ArrayList<>());
        results.put(evenNumbersSubscriber, new ArrayList<>());

        // simple notification sender which just logs the message
        // todo - handle the returned subscription ID
        SubscriptionSender notificationSender = (clientId, dataToSend) -> {
            if (dataToSend instanceof Integer) {
                results.get(clientId).add((Integer)dataToSend);
            }
        };

        // supplies Predicate<Integer> which returns true if number is odd
        PredicateSupplier<SubscribeIntegers,Integer> oddNumberPredicateSupplier = originalRequest -> integer -> {
            final boolean isOdd = (integer & 1) == 1;
            return originalRequest.parity == Parity.ODD ? isOdd : !isOdd;
        };

        // will be digested in the initial subscribe request
        DataSupplier<Integer> dataSupplier = reqBody -> Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        // init the subscription server
        SubscriptionServerBase<SubscribeIntegers,Integer> integersSubscriptionServer = new SubscriptionServerBaseImpl<>(notificationSender, oddNumberPredicateSupplier, dataSupplier);

        // subscribe for odd numbers
        String oddNumbersSubscriptionId = integersSubscriptionServer.onSubscribe(oddNumbersSubscriber, new SubscribeIntegers(Parity.ODD), null);

        // subscribe for even numbers
        String evenNumbersSubscriptionId = integersSubscriptionServer.onSubscribe(evenNumbersSubscriber, new SubscribeIntegers(Parity.EVEN), null);

        // number events - should trigger notifications for the subscribers
        IntStream.range(10,20).forEach(value -> integersSubscriptionServer.onEvent(new EventInfo(null, null, value)));

        Assert.assertArrayEquals(results.get(oddNumbersSubscriber).toArray(), new Integer[]{1, 3, 5, 7, 9, 11, 13, 15, 17, 19});
        Assert.assertArrayEquals(results.get(evenNumbersSubscriber).toArray(), new Integer[]{0, 2, 4, 6, 8, 10, 12, 14, 16, 18});

        // todo - need to change server api to handle UnSubscribe messages
        // unsubscribe subscriber for odd numbers
        integersSubscriptionServer.onUnSubscribe(oddNumbersSubscriber, null, oddNumbersSubscriptionId);

        // unsubscribe subscriber for even numbers
        integersSubscriptionServer.onUnSubscribe(evenNumbersSubscriber, null, evenNumbersSubscriptionId);
    }

}