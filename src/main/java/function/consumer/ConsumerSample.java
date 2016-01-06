package function.consumer;

import hibrnate.validator.ServicesInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by eladw on 2/5/2015.
 */
public class ConsumerSample<T> {


    private final Map<Class<T>, Consumer<ServicesInfo>> validatorHandlers = new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(ConsumerSample.class);

    public ConsumerSample() {
//        validatorHandlers.put((Class<T>) MsgPublishPayLoadDto.class, (t) -> new MCCAMsgValidator().validate(t));
//        validatorHandlers.put((Class<T>) ChatStatePublishPayloadDto.class, (t) -> new ChatStatePublishValidator().validate(t));
//        validatorHandlers.put((Class<T>) QueryMessagesPayloadDto.class, (t) -> new QueryMessagesValidator().validate(t));
//        validatorHandlers.put((Class<T>) MsgAcceptStatusPublishPayloadDto.class, (t) -> new MsgAcceptStatusPublishValidator().validate(t));

    }

    public void runValidator(ServicesInfo servicesInfo){
        try{
            Consumer<ServicesInfo> validatorToUse = this.validatorHandlers.get(servicesInfo.getSourceDto().getPayload().getClass());
            validatorToUse.accept(servicesInfo);
        } catch (Exception e) {
            logger.info("Failed to find validator " + e);
        }
    }


}
