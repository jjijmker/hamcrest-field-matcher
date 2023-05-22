package nl.ijmker.fieldmatcher;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ExampleSubFieldsMatcher extends AbstractFieldMatcher<ExampleSubFields> {

    private ExampleSubFieldsMatcher(ExampleSubFields fieldsForComparison, String[] ignoredFieldNames) {
        super(fieldsForComparison, ignoredFieldNames);
    }

    public static ExampleSubFieldsMatcher hasSubFieldsOf(ExampleSubFields fieldsForComparison,
                                                         String... ignoredFieldNames) {
        return new ExampleSubFieldsMatcher(fieldsForComparison, ignoredFieldNames);
    }

    public static ObjectMatcher hasSubFieldsOf(Object object,
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

    public ObjectMatcher toObjectMatcher() {
        return new ObjectMatcher(this);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public class ObjectMatcher extends TypeSafeMatcher<Object> {

        private final ExampleSubFieldsMatcher matcher;

        @Override
        protected boolean matchesSafely(Object object) {
            if (object instanceof ExampleSubFields) {
                return matcher.matchesSafely((ExampleSubFields) object);
            } else {
                return false;
            }
        }

        @Override
        public void describeTo(Description description) {
            matcher.describeTo(description);
        }
    }
}
