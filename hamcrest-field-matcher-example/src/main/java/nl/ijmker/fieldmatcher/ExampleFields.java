package nl.ijmker.fieldmatcher;

import java.util.List;

public interface ExampleFields {

    String getField1();

    String getField2();

    String getField3();

    List<? extends ExampleSubFields> getSubFields();
}
