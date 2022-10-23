package nl.ijmker.fieldmatcher;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ExampleFieldsMatcherErrorMatcher extends TypeSafeMatcher<String> {

    private final String objectName;

    private final String fieldName;

    private final Object expectedValue;

    private final Object actualValue;

    /**
     * Expected: "ExampleObject1" with
     * "field1" equal to "field11"
     * but: "ExampleObject1" with
     * "field1" equal to "field1"
     */
    @Override
    protected boolean matchesSafely(String errorMessage) {
        log.info("error={}", errorMessage);
        String normalisedMessage = StringUtils.normalizeSpace(errorMessage);
        log.info("normalised={}", normalisedMessage);
        int butIndex = normalisedMessage.indexOf("but");
        String expectedMessage = normalisedMessage.substring(0, butIndex - 1);
        String butMessage = normalisedMessage.substring(butIndex);
        log.info("expected={}", expectedMessage);
        log.info("but={}", butMessage);
        return expectedMessage.startsWith(String.format("Expected: \"%s\" with", objectName))
                && expectedMessage.contains(String.format("\"%s\" equal to \"%s\"", fieldName, expectedValue))
                && butMessage.startsWith(String.format("but: \"%s\" with", objectName))
                && butMessage.contains(String.format("\"%s\" equal to \"%s\"", fieldName, actualValue));
    }

    @Override
    public void describeTo(Description description) {

    }

    public static ExampleFieldsMatcherErrorMatcher describesMismatch(String objectName, String fieldName,
                                                                     Object expectedValue, Object actualValue) {
        return new ExampleFieldsMatcherErrorMatcher(objectName, fieldName, expectedValue, actualValue);
    }
}
