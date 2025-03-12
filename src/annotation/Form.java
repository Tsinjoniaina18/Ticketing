package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Form {
    public String actionForm() default "";
    public String methodForm() default "";
    public String idForm() default "";
    public String classForm() default "";
}
