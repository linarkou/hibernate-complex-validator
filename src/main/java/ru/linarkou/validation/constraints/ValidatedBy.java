package ru.linarkou.validation.constraints;

import ru.linarkou.validation.Validator;
import ru.linarkou.validation.constraintvalidators.ValidatedByProcessor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ValidatedByProcessor.class)
public @interface ValidatedBy {
    String message() default "Custom validator failed";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    Class<? extends Validator<?>> value();
}
