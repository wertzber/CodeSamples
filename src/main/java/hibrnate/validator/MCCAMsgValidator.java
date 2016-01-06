package hibrnate.validator;


import hibrnate.validator.error.RejectCodes;
import hibrnate.validator.error.RejectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

/**
 * Created by eladw on 2/1/2015.
 */
public class MCCAMsgValidator  {

    private static Logger logger = LoggerFactory.getLogger(MCCAMsgValidator.class);

    public void validate(ServicesInfo servicesInfo) {

        PayloadDto msg = servicesInfo.getSourceDto().getPayload();
        logger.info("## start MsgPublishValidator");

            // Validation
            Set<ConstraintViolation<PayloadDto>> validateResults = Validation.buildDefaultValidatorFactory().getValidator().validate(msg);
            validateResults.forEach(n -> logger.info(n.toString()));
            servicesInfo.setLastSuccess(true).setLastServiceType(ServicesInfo.ServicesTypes.Validator);

            if(validateResults != null && !validateResults.isEmpty()) {
                servicesInfo.setLastSuccess(false);
                StringBuilder violationStr = new StringBuilder();
                for(ConstraintViolation<?> cv : validateResults){
                    violationStr.append(cv.getMessage()).append(",");
                }
                logger.info("Errors:\n" + violationStr.toString());
                //add reject
                servicesInfo.getRejectsCollector().addReject(new RejectService(RejectCodes.WRONG_PARAMETER, violationStr.toString()));
            }

       }



}