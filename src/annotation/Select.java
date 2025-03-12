package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Select {
    String nameSelect() default "";
    String idSelect() default "";
    String classSelect() default "";
    String reference() default "";
    String referenceFieldName() default "";
    String referenceFieldValue() default "";
}
