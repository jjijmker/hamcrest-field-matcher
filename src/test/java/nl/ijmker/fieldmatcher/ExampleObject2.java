package nl.ijmker.fieldmatcher;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class ExampleObject2 implements ExampleFields {

    String field1;

    String field2;

    String field3;

    Set<ExampleSubObject1> subFields;
}
