package nl.ijmker.fieldmatcher;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExampleObject1 implements ExampleFields {

    String field1;

    String field2;

    String field3;


}
