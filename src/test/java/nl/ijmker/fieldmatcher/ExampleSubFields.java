package nl.ijmker.fieldmatcher;

public interface ExampleFields {

    String getField1();

    String getField2();

    String getField3();

    Set<? extends ExampleSubFields> getSubFields();
}
