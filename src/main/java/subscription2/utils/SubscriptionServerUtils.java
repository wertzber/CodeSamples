package subscription2.utils;

import subscription.api.SubscriptionConverter;

import java.util.function.Function;

/**
 * Created with IntelliJ IDEA.
 * User: ofirp
 * Date: 1/26/2016
 * Time: 2:53 PM
 */
public class SubscriptionServerUtils {

    public static SubscriptionConverter getDefaultSubscriptionConverter() {
        return new SubscriptionConverter() {
            @Override
            public Object convert(Object input) {
                return input;
            }

            @Override
            public Object convert(String subscribeId, Object input) {
                return input;
            }
        };
    }

    public static Function getDefaultEventFilter() {
        return o -> o;
    }
}
