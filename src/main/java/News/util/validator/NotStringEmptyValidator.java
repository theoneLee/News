package News.util.validator;

import News.util.annotation.NotStringEmpty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Lee on 2017/6/16 0016.
 */
public class NotStringEmptyValidator implements ConstraintValidator<NotStringEmpty,String>{
    @Override
    public void initialize(NotStringEmpty constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value!=null&&!value.equals("")){
            System.out.println("校验已运行：true");
            return true;
        }
        System.out.println("校验已运行：false");
        return false;
    }
}
