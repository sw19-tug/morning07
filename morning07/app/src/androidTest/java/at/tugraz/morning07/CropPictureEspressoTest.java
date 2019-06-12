package at.tugraz.morning07;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class CropPictureEspressoTest {
    @Rule
    public ActivityTestRule<BigImageActivity> activityRule = new ActivityTestRule<>(BigImageActivity.class);

    @Test
    public void testCropButtonVisible()
    {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Crop")).check(matches(isDisplayed()));
    }

    @Test
    public void testCropButtonWorks()
    {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Crop")).check(matches(isDisplayed())).perform(click());
    }
}
