package hibrnate.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eladw on 2/4/2015.
 * pom:
 *
 <dependency>
 <groupId>org.hibernate</groupId>
 <artifactId>hibernate-validator</artifactId>
 <version>5.1.3.Final</version>
 </dependency>
 <dependency>
 <groupId>javax.el</groupId>
 <artifactId>javax.el-api</artifactId>
 <version>2.2.4</version>
 </dependency>
 <dependency>
 <groupId>org.glassfish.web</groupId>
 <artifactId>javax.el</artifactId>
 <version>2.2.4</version>
 </dependency>
 *
 *
 */
public class EnumAnnotationValidator implements ConstraintValidator<EnumAnnotation, String> {

    List<String> valueList = null;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!valueList.contains(value.toUpperCase())) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(EnumAnnotation constraintAnnotation) {
        valueList = new ArrayList<>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();

        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = enumClass.getEnumConstants();

        for (@SuppressWarnings("rawtypes")
        Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }
    }
}

