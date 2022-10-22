# hamcrest-field-matcher

Hamcrest matcher for matching fields of objects of different classes that implement the same interface.

Typical use case is an application with many similar (but not equal) objects that are mapped to each other using, say, MapStruct.

This matcher will help you validate your mappers.

1. Create a common interface (for example: `ExampleFields`)
2. Extend AbstractFieldMatcher (for example `ExampleFieldsMatcher`)
3. Use the configurator to list the fields you are interested in, and how to retrieve them:

```java
configurer
  .addMatchedField("field1", exampleFields -> exampleFields.getField1())
  .addMatchedField("field2", exampleFields -> exampleFields.getField2())
  .addMatchedField("field3", exampleFields -> exampleFields.getField3());
```

## Benefits

* Only non-matching fields and values are shown

```log
Expected: "ExampleObject1" with 
		"field1" equal to "field11"
		"field2" equal to "field22"
     but: "ExampleObject1" with 
		"field1" equal to "field1"
		"field2" equal to "field2"
```

## Why not `CoreMatchers.is()` & an `equals()` method?

* The objects are not equal, they just have the same fields
* Nicer error messages
