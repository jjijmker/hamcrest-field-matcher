package nl.ijmker.bankentitlement.common.hamcrest;

import lombok.Builder;
import lombok.Value;
import org.hamcrest.Description;

import java.util.Set;

@Value
@Builder
public class FieldMatcherResult {

    private Class objectClass;

    private Set<FieldMatcherFailure> failures;

    public boolean allFieldsMatch() {
        return failures.isEmpty();
    }

    public void describeExpectedValue(Description description) {
        description
                .appendValue(objectClass.getSimpleName())
                .appendText(" with ");
        failures.forEach(failure -> description
                .appendText("\n\t\t")
                .appendValue(failure.getFieldName())
                .appendText(" equal to ")
                .appendValue(failure.getExpectedValue()));
    }

    public void describeActualValue(Description description) {
        description
                .appendValue(objectClass.getSimpleName())
                .appendText(" with ");
        failures.forEach(failure -> description
                .appendText("\n\t\t")
                .appendValue(failure.getFieldName())
                .appendText(" equal to ")
                .appendValue(failure.getActualValue()));
    }
}
