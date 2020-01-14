package ru.linarkou.validation.constraintvalidators;

import ru.linarkou.validation.Validator;
import ru.linarkou.validation.constraints.ValidatedBy;
import ru.linarkou.validation.exception.ValidatorInstantiationException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidatedByProcessor implements ConstraintValidator<ValidatedBy, Object> {
    private Class<? extends Validator> validatorClass;

    @Override
    public void initialize(ValidatedBy constraintAnnotation) {
        validatorClass = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Validator validator;
        try {
            validator = validatorClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ValidatorInstantiationException("Cannot instantiate validator", e);
        }
        List<String> validMessages = validator.isValid(value);
        boolean isValid = validMessages.isEmpty();
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            for (String validMessage : validMessages) {
                context.buildConstraintViolationWithTemplate(validMessage).addConstraintViolation();
            }
        }
        return isValid;
    }
}
