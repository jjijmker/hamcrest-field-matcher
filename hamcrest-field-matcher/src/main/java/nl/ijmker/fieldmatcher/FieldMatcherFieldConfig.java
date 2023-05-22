package nl.ijmker.fieldmatcher;

import lombok.Builder;
import lombok.Getter;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;

import java.util.function.Function;

@Getter
@Builder
public class FieldMatcherFieldConfig<A> {

    private final String fieldName;

    private final Function<A, Object> valueSupplier;

    @Builder.Default
    private final Function<Object, Matcher<Object>> valueMatcher = fieldValue -> CoreMatchers.is(fieldValue);
}
