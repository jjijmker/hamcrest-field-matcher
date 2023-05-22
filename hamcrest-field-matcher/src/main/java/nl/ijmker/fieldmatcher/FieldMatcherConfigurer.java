package nl.ijmker.fieldmatcher;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hamcrest.Matcher;

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

    public FieldMatcherConfigurer<A> addField(String fieldName, Function<A, Object> valueSupplier) {
        return addField(fieldName, valueSupplier, null);
    }

    public FieldMatcherConfigurer<A> addField(String fieldName, Function<A, Object> valueSupplier,
                                              Function<Object, Matcher<Object>> valueMatcher) {
        matchedFields.add((FieldMatcherFieldConfig<A>) FieldMatcherFieldConfig.builder()
                .fieldName(fieldName)
                .valueSupplier((Function<Object, Object>) valueSupplier)
                .valueMatcher(valueMatcher)
                .build());
        return this;
    }
}
