# Hamcrest Field Matcher

Hamcrest matcher for matching fields of 2 objects:

* Objects must implement same interface (but do not need to be of same type)
* Error messages list only fields with mismatched values
* Supports collection values (Set, List)

Ideal for validating mappers!

## Implementation

Create interface that describes the matched fields:

```java
public interface ExampleFields {
    String getField1();
    String getField2();
    String getField3();
}
```

Extend the provided abstract matcher, listing matched fields and how to retrieve values:

```java
public class ExampleFieldsMatcher extends AbstractFieldMatcher<ExampleFields> {

    @Override
    protected void configure(FieldMatcherConfigurer<ExampleFields> configurer) {
        configurer
                .addMatchedField("field1", exampleFields -> exampleFields.getField1())
                .addMatchedField("field2", exampleFields -> exampleFields.getField2())
                .addMatchedField("field3", exampleFields -> exampleFields.getField3());
    }
}
```

Create classes for comparison:

```java
@Value
@Builder
public class ExampleObject1 implements ExampleFields {
    String field1;
    String field2;
    String field3;
    Set<ExampleSubObject1> subFields;
}
```

Compare objects of different types but implementing the same interface:

```java
ExampleObject1 actual = ExampleObject1.builder()
    .field1("field1")
    .field2("field2")
    .field3("field3")
    .build();
ExampleObject2 expected = ExampleObject2.builder()
    .field1("field11")
    .field2("field22")
    .field3("field3")
    .build();
assertThat(actual, hasFieldsOf(expected));
```

Error description looks like this:

```text
Expected: "ExampleObject1" with 
		"field1" equal to "field11"
		"field2" equal to "field22"
     but: "ExampleObject2" with 
		"field1" equal to "field1"
		"field2" equal to "field2"
```

Fields can be ignored:

```java
assertThat(actual, hasFieldsOf(expected, "field1", "field2" /* ignored */));
```

## Collection Matching

Collections can be matched if elements implement the same interface:

```java
public interface ExampleFields {
    Set<? extends ExampleSubFields> getSubFields();
}
```

An identifying field of the collection item must be selected:

```java
public class ExampleFieldsMatcher extends AbstractFieldMatcher<ExampleFields> {

    @Override
    protected void configure(FieldMatcherConfigurer<ExampleFields> configurer) {
        configurer
                .addMatchedField("subFields",
                        exampleFields -> exampleFields.getSubFields()
                                .stream()
                                .map(ExampleSubFields::getSubField1)
                                .collect(Collectors.toSet()));
    }
}
```

Error message looks like:

```text
Expected: "ExampleObject1" with 
		"subField" equal to <"subField1", "subField2">
     but: "ExampleObject2" with 
		"subField" equal to <"subField11", "subField2">
```

## Validating Errors

The package contains a separate matcher for validating expected errors:

```java
AssertionError assertionError = assertThrows(AssertionError.class, () -> {
    assertThat(actual, hasFieldsOf(expected));
});

assertThat(assertionError.getMessage(), describesMismatch(
    "ExampleObject1", "field1", expected.getField1(), actual.getField1()));
assertThat(assertionError.getMessage(), describesMismatch(
    "ExampleObject1", "field2", expected.getField2(), actual.getField2()));
```

