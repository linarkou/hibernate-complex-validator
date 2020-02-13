# hibernate-complex-validator
Tool for complex validation rules for use with Bean Validation API

## Usage
1. Add `@ValidatedBy` on yout data-class that needs some complex validation:
  ```
    @ValidatedBy(SomeDataClassValidator.class)
    private class SomeDataClass {
        private String first;
        private String second;
    }
  ```
2. Implement `Validator<T>` with some extra logic:
  ```
  public class SomeDataClassValidator implements Validator<SomeDataClass> {
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
  ```

You can also return List with property name already defined in your `ValidationMessages.properties` file:
```
public class SomeDataClassValidator implements Validator<SomeDataClass> {
    @Override
    public List<String> isValid(SomeDataClass value) {
        List<String> validMessages = new ArrayList<>();
        boolean isValid = value.getFirst() == null || value.getSecond() != null;
        if (!isValid) {
*             validMessages.add("{some.validation.message}");
        }
        return validMessages;
    }
}
```
and `ValidationMessages.properties` should contains:
```
some.validation.message='first' shouldn't exist without 'second'!
```
