package nl.ijmker.fieldmatcher;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

@Slf4j
@Data
@RequiredArgsConstructor
public abstract class AbstractFieldMatcher<A> extends TypeSafeMatcher<A> {

    private final A expectedValue;

    private final String[] ignoredFieldNames;

    private FieldMatcherResult result;

    @Override
    protected boolean matchesSafely(A objectValue) {
        // Let the subclass list the fields, and how to get values
        FieldMatcherConfigurer<A> configurer = FieldMatcherConfigurer.create();
        configure(configurer);
        // Create objectValue result
        result = FieldMatcherResult.builder()
                .objectClass(objectValue.getClass())
                .failures(configurer.getMatchedFields().stream()
                        // Go through all known fields and see if they have mismatches
                        .flatMap(fieldConfig -> {
                            // If the field is listed as ignored, then do not check for a mismatch
                            if (Arrays.asList(ignoredFieldNames).contains(fieldConfig.getFieldName())) {
                                return Stream.empty();
                            }
                            // Determine the expected and actual field values from the object value
                            Object expectedFieldValue = fieldConfig.getValueSupplier().apply(expectedValue);
                            Object actualFieldValue = fieldConfig.getValueSupplier().apply(objectValue);
                            // If there is a mismatch for the field, add a failure
                            if (isMisMatch(expectedFieldValue, actualFieldValue, fieldConfig.getValueMatcher())) {
                                return Stream.of(FieldMatcherFailure.builder()
                                        .fieldName(fieldConfig.getFieldName())
                                        .expectedValue(expectedFieldValue)
                                        .actualValue(actualFieldValue)
                                        .build());

                            } else {
                                return Stream.empty();
                            }
                        })
                        .collect(Collectors.toSet()))
                .build();
        // Get result
        return result.allFieldsMatch();
    }

    private static boolean isMisMatch(Object expectedAttributeValue, Object actualAttributeValue, Function<Object,
            Matcher<Object>> valueMatcher) {
        // A mismatch occurs ...
        if (actualAttributeValue == null) {
            // (1) ... when the actual value is NULL and the expected value is not
            return expectedAttributeValue != null;
        } else if (expectedAttributeValue instanceof Collection<?> && actualAttributeValue instanceof Collection<?>) {
            // (2) ... when the actual and expected field values are collections and ...
            Collection<?> expectedCollection = (Collection<?>) expectedAttributeValue;
            Collection<?> actualCollection = (Collection<?>) actualAttributeValue;
            if (expectedCollection.isEmpty() && actualCollection.isEmpty()) {
                // (2a) ... one is empty and the other is not
                return false;
            } else if (valueMatcher != null) {
                // (2b) ... TODO
                Set<Matcher<Object>> itemMatchers = expectedCollection.stream()
                        .map(expectedItem -> valueMatcher.apply(expectedItem))
                        .collect(Collectors.toSet());
                return !containsInAnyOrder(itemMatchers).matches(actualCollection);
            } else {
                // (2c) ... all elements in one are not contained in the other
                return !expectedCollection.containsAll(actualCollection) || !actualCollection.containsAll(expectedCollection);
            }
        } else if (valueMatcher != null) {
            // (3) ... TODO
            return !valueMatcher.apply(expectedAttributeValue).matches(actualAttributeValue);
        } else {
            // (4) ... when the expected and actual values are not equal
            return !actualAttributeValue.equals(expectedAttributeValue);
        }
    }

    /**
     * Describes expected value
     */
    @Override
    public void describeTo(Description description) {
        result.describeExpectedValue(description);
    }

    @Override
    protected void describeMismatchSafely(A value, Description description) {
        result.describeActualValue(description);
    }

    protected abstract void configure(FieldMatcherConfigurer<A> configurer);

    public TypeSafeMatcher<Object> toObjectMatcher() {
        return new ObjectMatcher(this);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public class ObjectMatcher extends TypeSafeMatcher<Object> {

        private final TypeSafeMatcher matcher;


        @Override
        public void describeTo(Description description) {
            matcher.describeTo(description);
        }

        @Override
        protected boolean matchesSafely(Object o) {
            return matcher.matches(o);
        }
    }
}
