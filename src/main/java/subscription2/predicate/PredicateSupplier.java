package subscription2.predicate;

import java.util.function.Predicate;

/**
 * Created with IntelliJ IDEA.
 * User: ofirp
 * Date: 2/1/2016
 * Time: 7:01 PM
 */
public interface PredicateSupplier<O,P> {

    Predicate<P> get(O originalRequest);

}
