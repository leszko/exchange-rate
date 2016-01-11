package com.summercoding.zooplus.register.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CountryConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Country {
    String message() default "{Country}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
