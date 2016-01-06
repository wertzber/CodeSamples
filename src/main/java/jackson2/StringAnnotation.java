package jackson2;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by eladw on 2/4/2015.
 */
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = StringAnnotationValidator.class)
@Documented
public @interface StringAnnotation {

    String message() default "{default message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value();


}
