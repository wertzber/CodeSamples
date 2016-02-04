package subscription2.predicate;

import org.apache.commons.lang.Validate;

import javax.validation.constraints.NotNull;
import java.util.function.Predicate;

/**
 * Determines the {@link ChangeType} for given old/new state of an Object according to supplied predicate
 *
 * Created with IntelliJ IDEA.
 * User: ofirp
 * Date: 1/31/2016
 * Time: 11:41 AM
 */
public class ChangeTypeEvaluator {

    /**
     * Determines the {@link ChangeType} for given old/new state of an Object according to supplied predicate
     * @param predicate the predicate to use for the evaluation
     * @param oldState the state of the object before it was changed
     * @param newState the state of the object after it was changed
     * @return {@link ChangeType}
     */
    public static <T> ChangeType evaluate(Predicate<T> predicate, T oldState, T newState) {
        Validate.notNull(newState, "newState cannot be null");
        if (oldState != null) {
            if (predicate.test(newState)) {
                return ChangeType.UPSERT;
            } else if (predicate.test(oldState)) {
                return ChangeType.DELETE;
            }
        } else {
            if (predicate.test(newState)) {
                return ChangeType.UPSERT;
            }
        }
        return ChangeType.NO_CHANGE;
    }
}
