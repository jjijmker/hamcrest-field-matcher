package nl.ijmker.bankentitlement.common.hamcrest;

import lombok.Builder;
import lombok.Getter;

import java.util.function.Function;

@Getter
@Builder
public class FieldMatcherFieldConfig<A> {

    private final String fieldName;

    private final Function<A, Object> valueSupplier;
}
