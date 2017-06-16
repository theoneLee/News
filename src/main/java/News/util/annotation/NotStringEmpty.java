package News.util.annotation;

import News.util.validator.NotStringEmptyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Lee on 2017/6/16 0016.
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotStringEmptyValidator.class)

public @interface NotStringEmpty {
    String message() default "not_string_empty";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
