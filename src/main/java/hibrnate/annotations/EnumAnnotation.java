package hibrnate.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Created by eladw on 2/4/2015.
 */
@Documented
@Constraint(validatedBy = EnumAnnotationValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@NotNull(message = "Value cannot be null")
//@ReportAsSingleViolation
public @interface EnumAnnotation {

    Class<? extends Enum<?>> enumClazz();

    String message() default "Value is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
