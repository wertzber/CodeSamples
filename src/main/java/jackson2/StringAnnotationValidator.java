package jackson2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * Created by eladw on 2/4/2015.
 */
public class StringAnnotationValidator implements ConstraintValidator<StringAnnotation, String> {

    private static Logger logger = LoggerFactory.getLogger(StringAnnotationValidator.class);

    List<String> referenceValues;

    @Override
    public void initialize(StringAnnotation input) {
        if(input != null){
            this.referenceValues = Arrays.asList(input.value().split(","));
        } else {
            this.referenceValues = null;
        }
    }

    public boolean isValid(String inputStr, ConstraintValidatorContext constraintContext) {

        if (inputStr == null)
            return false;

        return referenceValues.stream().anyMatch(node -> {
            logger.debug("input annotation: " + inputStr + " , reference: " + node);
            return node.equalsIgnoreCase(inputStr);
        });
    }
}

