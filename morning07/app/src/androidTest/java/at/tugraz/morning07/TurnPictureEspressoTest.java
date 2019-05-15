package at.tugraz.morning07;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

public class TurnPictureEspressoTest {
    @Rule
    public ActivityTestRule<BigImageActivity> activityRule = new ActivityTestRule<>(BigImageActivity.class);

    public static Matcher<View> withRotation(final float rotation) {
        return new ImageTurnMatcher(rotation);
    }

    @Test
    public void testTurnButtonVisible() {
        onView(withId(R.id.turnButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testTurnButtonWorks(){
        onView(withId(R.id.turnButton)).perform(click());
        onView(withId(R.id.big_image)).check(matches(withRotation(90)));
    }
    @Test
    public void testFullRotation(){
        onView(withId(R.id.turnButton)).perform(click());
        onView(withId(R.id.big_image)).check(matches(withRotation(90)));
        onView(withId(R.id.turnButton)).perform(click());
        onView(withId(R.id.big_image)).check(matches(withRotation(180)));
        onView(withId(R.id.turnButton)).perform(click());
        onView(withId(R.id.big_image)).check(matches(withRotation(270)));
        onView(withId(R.id.turnButton)).perform(click());
        onView(withId(R.id.big_image)).check(matches(withRotation(0)));
    }

    @Test
    public void testSaveButtonVisible(){
        onView(withId(R.id.saveButton)).check(matches(not(isDisplayed())));
        onView(withId(R.id.turnButton)).perform(click());
        onView(withId(R.id.saveButton)).check(matches(isDisplayed()));
    }
}
