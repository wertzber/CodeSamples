package guice.example3;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Created by eladw on 9/1/2016.
 */


@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
@Target( { METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
public @interface Screen {
}
