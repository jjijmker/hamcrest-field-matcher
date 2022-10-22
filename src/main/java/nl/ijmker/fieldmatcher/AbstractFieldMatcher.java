package nl.ijmker.bankentitlement.common.hamcrest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractFieldMatcher<A> extends TypeSafeMatcher<A> {

    private final A fieldsForComparison;

    private final String[] excludedFieldNames;

    private FieldMatcherResult result;

    @Override
    protected boolean matchesSafely(A a) {
        // Let the subclass list the fields, and how to get values
        FieldMatcherConfigurer<A> configurer = FieldMatcherConfigurer.create();
        configure(configurer);
        // Create a result
        result = FieldMatcherResult.builder()
                .objectClass(a.getClass())
                .failures(configurer.getMatchedFields().stream()
                        .map(fieldConfig -> FieldMatcherFailure.builder()
                                .fieldName(fieldConfig.getFieldName())
                                .expectedValue(fieldConfig.getValueSupplier().apply(fieldsForComparison))
                                .actualValue(fieldConfig.getValueSupplier().apply(a))
                                .build())
                        .filter(this::isNotExcluded)
                        .filter(this::isMatched)
                        .collect(Collectors.toSet()))
                .build();
        // Get result
        return result.allFieldsMatch();
    }

    private boolean isNotExcluded(FieldMatcherFailure failure) {
        return Stream.of(excludedFieldNames)
                .noneMatch(excludedFieldName -> failure.getFieldName().equals(excludedFieldName));
    }

    private boolean isMatched(FieldMatcherFailure failure) {
        if (failure.getActualValue() instanceof Collection<?> && failure.getExpectedValue() instanceof Collection<?>) {
            Collection<?> expectedCollection = (Collection<?>) failure.getExpectedValue();
            Collection<?> actualCollection = (Collection<?>) failure.getActualValue();
            return !expectedCollection.containsAll(actualCollection) || !actualCollection.containsAll(expectedCollection);
        } else {
            return !failure.getActualValue().equals(failure.getExpectedValue());
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
}
