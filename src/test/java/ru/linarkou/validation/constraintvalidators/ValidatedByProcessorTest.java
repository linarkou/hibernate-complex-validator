package ru.linarkou.validation.constraintvalidators;

import org.junit.Before;
import org.junit.Test;
import ru.linarkou.validation.Validator;
import ru.linarkou.validation.constraints.ValidatedBy;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;

public class ValidatedByProcessorTest {
    private javax.validation.Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidationOK() {
        SomeDataClass someData = new SomeDataClass();
        someData.setFirst("1");
        someData.setSecond("2");

        Set<ConstraintViolation<SomeDataClass>> constraintViolations = validator.validate(someData);

        assertThat(constraintViolations, empty());
    }

    @Test
    public void testValidationFailed() {
        SomeDataClass someData = new SomeDataClass();
        someData.setFirst("1");
        someData.setSecond(null);

        Set<ConstraintViolation<SomeDataClass>> constraintViolations = validator.validate(someData);

        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations, hasItem(
                hasProperty("message", containsString("'first' shouldn't exist without 'second'!"))));
    }

    // example of Validator
    public static class SomeDataClassValidator implements Validator<SomeDataClass> {
        @Override
        public List<String> isValid(SomeDataClass value) {
            List<String> validMessages = new ArrayList<>();
            boolean isValid = value.getFirst() == null || value.getSecond() != null;
            if (!isValid) {
                validMessages.add("'first' shouldn't exist without 'second'!");
            }
            return validMessages;
        }
    }

    //example of using @ValidatedBy
    @ValidatedBy(SomeDataClassValidator.class)
    private class SomeDataClass {
        private String first;
        private String second;

        public String getFirst() {
            return first;
        }

        public String getSecond() {
            return second;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public void setSecond(String second) {
            this.second = second;
        }
    }
}