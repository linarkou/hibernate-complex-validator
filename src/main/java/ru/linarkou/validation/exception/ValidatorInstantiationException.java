package ru.linarkou.validation.exception;

public class ValidatorInstantiationException extends RuntimeException {
    public ValidatorInstantiationException() {
    }

    public ValidatorInstantiationException(String s) {
        super(s);
    }

    public ValidatorInstantiationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ValidatorInstantiationException(Throwable throwable) {
        super(throwable);
    }
}
