package nl.ijmker.fieldmatcher;

import org.hamcrest.Matcher;

import java.util.function.Function;

import static nl.ijmker.fieldmatcher.ExampleSubFieldsMatcher.hasSubFieldsOf;

public class ExampleFieldsMatcher extends AbstractFieldMatcher<ExampleFields> {

    private ExampleFieldsMatcher(ExampleFields fieldsForComparison, String[] ignoredFieldNames) {
        super(fieldsForComparison, ignoredFieldNames);
    }

    public static ExampleFieldsMatcher hasFieldsOf(ExampleFields fieldsForComparison, String... ignoredFieldNames) {
        return new ExampleFieldsMatcher(fieldsForComparison, ignoredFieldNames);
    }

    @Override
    protected void configure(FieldMatcherConfigurer<ExampleFields> configurer) {
        Function<Object, Matcher<Object>> subFieldsMatcher = subFields -> hasSubFieldsOf(subFields);
        configurer
                .addField("field1", exampleFields -> exampleFields.getField1())
                .addField("field2", exampleFields -> exampleFields.getField2())
                .addField("field3", exampleFields -> exampleFields.getField3())
                .addField("subFields", exampleFields -> exampleFields.getSubFields(), subFieldsMatcher);
    }
}
