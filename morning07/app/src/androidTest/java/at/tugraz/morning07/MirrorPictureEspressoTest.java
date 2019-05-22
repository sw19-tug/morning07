package at.tugraz.morning07;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MirrorPictureEspressoTest {

    @Rule
    public ActivityTestRule<BigImageActivity> activityRule = new ActivityTestRule<>(BigImageActivity.class);

    @Test
    public void testMirrorHorizontalButtonVisible() {
        onView(withId(R.id.mirrorHorizontalButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testMirrorVerticalButtonVisible() {
        onView(withId(R.id.mirrorVerticalButton)).check(matches(isDisplayed()));
    }

}
