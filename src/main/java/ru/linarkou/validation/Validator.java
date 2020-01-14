package ru.linarkou.validation;

import java.util.List;

public interface Validator<T> {
    List<String> isValid(T value);
}
