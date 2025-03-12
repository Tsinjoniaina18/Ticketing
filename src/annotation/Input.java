package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Input {
    String typeInput() default "text";
    String nameInput() default "";
    String idInput() default "";
    String placeholderInput() default "";
    String classInput() default "";
    String stepInput() default "";
}