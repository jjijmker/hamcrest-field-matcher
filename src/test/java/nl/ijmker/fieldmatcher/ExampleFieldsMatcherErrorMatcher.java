package nl.ijmker.fieldmatcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class HamcrestAssertionErrorMatcher extends TypeSafeMatcher<String> {
    @Override
    protected boolean matchesSafely(String s) {
        return false;
    }

    @Override
    public void describeTo(Description description) {

    }
}
