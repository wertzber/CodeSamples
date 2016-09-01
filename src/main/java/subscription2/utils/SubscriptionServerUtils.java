package subscription2.utils;

import subscription.api.SubscriptionConverter;

import java.util.function.Function;

/**
 * Created with IntelliJ IDEA.
 * User: ofirp
 * Date: 1/26/2016
 * Time: 2:53 PM
 */
public class SubscriptionServerUtils<P> {

    public SubscriptionConverter getDefaultSubscriptionConverter() {
        return new SubscriptionConverter<P,P>() {
            @Override
            public P convert(P object) {
                return object;
            }

            @Override
            public P convert(String subscribeId, P object) {
                return object;
            }
        };
    }

    public Function<P,P> getDefaultFilter() {
        return o -> o;
    }
}
