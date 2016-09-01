package subscription2.api;

import com.liveperson.api.ReqBody;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: ofirp
 * Date: 2/4/2016
 * Time: 11:08 AM
 */
public interface DataSupplier<T> {
    Collection<T> getHistoryData(ReqBody reqBody);
}
