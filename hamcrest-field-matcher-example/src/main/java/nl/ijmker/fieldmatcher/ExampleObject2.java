package nl.ijmker.fieldmatcher;

import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Value
@Builder
public class ExampleObject2 implements ExampleFields {

    String field1;

    String field2;

    String field3;

    List<ExampleSubObject2> subFields;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
