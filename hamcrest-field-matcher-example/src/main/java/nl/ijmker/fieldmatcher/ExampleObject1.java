package nl.ijmker.fieldmatcher;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ExampleObject1 implements ExampleFields {

    String field1;

    String field2;

    String field3;

    List<ExampleSubObject1> subFields;
}
