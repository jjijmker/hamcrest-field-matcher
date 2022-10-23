package nl.ijmker.fieldmatcher;

import org.junit.jupiter.api.Test;

import static nl.ijmker.fieldmatcher.ExampleFieldsMatcher.hasFieldsOf;
import static nl.ijmker.fieldmatcher.FieldMatcherErrorMatcher.describesMismatch;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExampleFieldsMatcherTest {

    @Test
    void matchingObjects() {
        // Given
        ExampleObject1 actual = ExampleObject1.builder()
                .field1("field1")
                .field2("field2")
                .field3("field4")
                .build();
        ExampleObject2 expected = ExampleObject2.builder()
                .field1("field1")
                .field2("field2")
                .field3("field4")
                .build();
        // When
        assertThat(actual, hasFieldsOf(expected));
        // Then
        // No AssertionError thrown
    }

    @Test
    void field1Mismatch() {
        // Given
        ExampleObject1 actual = ExampleObject1.builder()
                .field1("field1")
                .field2("field2")
                .field3("field4")
                .build();
        ExampleObject2 expected = ExampleObject2.builder()
                .field1("field11")
                .field2("field2")
                .field3("field4")
                .build();
        // When
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            assertThat(actual, hasFieldsOf(expected));
        });

        // Then
        assertThat(assertionError.getMessage(), describesMismatch(
                "ExampleObject1", "field1", expected.getField1(), actual.getField1()));
    }

    @Test
    void field1And2Mismatch() {
        // Given
        ExampleObject1 actual = ExampleObject1.builder()
                .field1("field1")
                .field2("field2")
                .field3("field4")
                .build();
        ExampleObject2 expected = ExampleObject2.builder()
                .field1("field11")
                .field2("field22")
                .field3("field4")
                .build();
        // When
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            assertThat(actual, hasFieldsOf(expected));
        });

        // Then
        assertThat(assertionError.getMessage(), describesMismatch(
                "ExampleObject1", "field1", expected.getField1(), actual.getField1()));
        assertThat(assertionError.getMessage(), describesMismatch(
                "ExampleObject1", "field2", expected.getField2(), actual.getField2()));
    }

    @Test
    void field1MismatchExcluded() {
        // Given
        ExampleObject1 actual = ExampleObject1.builder()
                .field1("field1")
                .field2("field2")
                .field3("field4")
                .build();
        ExampleObject2 expected = ExampleObject2.builder()
                .field1("field11")
                .field2("field2")
                .field3("field4")
                .build();
        // When
        assertThat(actual, hasFieldsOf(expected, "field1"));

        // Then
        // No AssertionError thrown
    }
}
