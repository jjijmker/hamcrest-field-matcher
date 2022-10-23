package nl.ijmker.fieldmatcher;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExampleSubObject2 implements ExampleSubFields {

    String subField1;

    String subField2;

    String subField3;
}
