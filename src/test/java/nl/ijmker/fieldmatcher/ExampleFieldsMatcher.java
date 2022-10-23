package nl.ijmker.fieldmatcher;

public class ExampleFieldsMatcher extends AbstractFieldMatcher<ExampleFields> {

    private ExampleFieldsMatcher(ExampleFields fieldsForComparison, String[] ignoredFieldNames) {
        super(fieldsForComparison, ignoredFieldNames);
    }

    public static ExampleFieldsMatcher hasFieldsOf(ExampleFields fieldsForComparison, String... ignoredFieldNames) {
        return new ExampleFieldsMatcher(fieldsForComparison, ignoredFieldNames);
    }

    @Override
    protected void configure(FieldMatcherConfigurer<ExampleFields> configurer) {
        configurer
                .addMatchedField("field1", exampleFields -> exampleFields.getField1())
                .addMatchedField("field2", exampleFields -> exampleFields.getField2())
                .addMatchedField("field3", exampleFields -> exampleFields.getField3());
    }
}
