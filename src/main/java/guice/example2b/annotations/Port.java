package guice.example2b.annotations;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
@Target( { METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
public @interface Port {
}
