package nl.ijmker.bankentitlement.common.hamcrest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldMatcherConfigurer<A> {
    @Getter
    private Set<FieldMatcherFieldConfig<A>> matchedFields = new HashSet<>();

    public static FieldMatcherConfigurer create() {
        return new FieldMatcherConfigurer();
    }

    public FieldMatcherConfigurer<A> addMatchedField(String fieldName, Function<A, Object> valueSupplier) {
        matchedFields.add((FieldMatcherFieldConfig<A>) FieldMatcherFieldConfig.builder()
                .fieldName(fieldName)
                .valueSupplier((Function<Object, Object>) valueSupplier)
                .build());
        return this;
    }
}
