package nl.ijmker.fieldmatcher;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FieldMatcherFailure {

    private final String fieldName;

    private final Object expectedValue;

    private final Object actualValue;
}
