package at.tugraz.morning07;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BlackAndWhiteFilterEspressoTest {
    @Rule
    public ActivityTestRule<BigImageActivity> activityRule = new ActivityTestRule<>(BigImageActivity.class);

    @Test
    public void testBlackAndWhiteButtonVisible() {
        onView(withId(R.id.blackAndWhiteButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testSaveButtonVisible(){
        onView(withId(R.id.saveButton)).check(matches(not(isDisplayed())));
        onView(withId(R.id.blackAndWhiteButton)).perform(click());
        onView(withId(R.id.saveButton)).check(matches(isDisplayed()));
    }
}
