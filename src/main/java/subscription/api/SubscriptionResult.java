package subscription.api;

import java.util.List;

/**
 * Created by eladw on 1/4/2016.
 * R - base result
 * F - convert base result to specific msg format for the DTO
 * W - add notification wrapper to F.
 */
public interface SubscriptionResult<R,F,W > {

    public R createBaseResult();
    public F convertResultToFormat(R source, F msgFormat);
    public W prepareNotificationWrapper(F msgFormat, W wrapperResponse);
    public W prepareNotificationWrapper(List<F> msgFormat, W wrapperResponse);



}
