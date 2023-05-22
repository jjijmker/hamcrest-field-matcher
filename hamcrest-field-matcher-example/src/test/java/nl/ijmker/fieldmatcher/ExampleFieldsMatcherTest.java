package nl.ijmker.fieldmatcher;

import org.junit.jupiter.api.Test;

import java.util.List;

import static nl.ijmker.fieldmatcher.ExampleFieldsMatcher.hasFieldsOf;
import static nl.ijmker.fieldmatcher.FieldMatcherErrorMatcher.describesMismatch;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExampleFieldsMatcherTest {

    @Test
    void testNoMismatches() {
        // Given
        ExampleObject1 actual = ExampleObject1.builder()
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .subFields(List.of(
                        ExampleSubObject1.builder()
                                .subField1("subField1")
                                .subField2("subField2")
                                .subField3("subField3")
                                .build(),
                        ExampleSubObject1.builder()
                                .subField1("subField4")
                                .subField2("subField5")
                                .subField3("subField6")
                                .build()))
                .build();
        ExampleObject2 expected = ExampleObject2.builder()
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .subFields(List.of(
                        ExampleSubObject2.builder()
                                .subField1("subField4")
                                .subField2("subField5")
                                .subField3("subField6")
                                .build(),
                        ExampleSubObject2.builder()
                                .subField1("subField1")
                                .subField2("subField2")
                                .subField3("subField3")
                                .build()))
                .build();
        // When
        assertThat(actual, hasFieldsOf(expected));
        // Then
        // No AssertionError thrown
    }

    @Test
    void testMismatchInField1() {
        // Given
        ExampleObject1 actual = ExampleObject1.builder()
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .build();
        ExampleObject2 expected = ExampleObject2.builder()
                .field1("field11")
                .field2("field2")
                .field3("field3")
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
    void testMismatchInField1AndField2() {
        // Given
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
    void testMismatchInField1ButField1IsExcluded() {
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
