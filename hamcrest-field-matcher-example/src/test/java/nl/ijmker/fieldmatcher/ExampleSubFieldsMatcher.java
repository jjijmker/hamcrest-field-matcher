package nl.ijmker.fieldmatcher;

import org.hamcrest.TypeSafeMatcher;

public class ExampleSubFieldsMatcher extends AbstractFieldMatcher<ExampleSubFields> {

    private ExampleSubFieldsMatcher(ExampleSubFields fieldsForComparison, String[] ignoredFieldNames) {
        super(fieldsForComparison, ignoredFieldNames);
    }

    public static ExampleSubFieldsMatcher hasSubFieldsOf(ExampleSubFields fieldsForComparison,
                                                         String... ignoredFieldNames) {
        return new ExampleSubFieldsMatcher(fieldsForComparison, ignoredFieldNames);
    }

    public static TypeSafeMatcher<Object> hasSubFieldsOf(Object object,
                                                         String... ignoredFieldNames) {
        if (object instanceof ExampleSubFields) {
            return hasSubFieldsOf((ExampleSubFields) object, ignoredFieldNames).toObjectMatcher();
        } else {
            throw new IllegalArgumentException("BOEM!");
        }
    }

    @Override
    protected void configure(FieldMatcherConfigurer<ExampleSubFields> configurer) {
        configurer
                .addField("subField1", subFields -> subFields.getSubField1())
                .addField("subField2", subFields -> subFields.getSubField2())
                .addField("subField3", subFields -> subFields.getSubField3());
    }
}
