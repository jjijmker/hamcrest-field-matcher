package nl.ijmker.fieldmatcher;

import java.util.Set;

public interface ExampleFields {

    String getField1();

    String getField2();

    String getField3();

    Set<? extends ExampleSubFields> getSubFields();
}
