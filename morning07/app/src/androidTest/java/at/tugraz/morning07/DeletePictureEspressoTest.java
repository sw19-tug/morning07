package at.tugraz.morning07;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;


@RunWith(AndroidJUnit4.class)
public class DeletePictureEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void isVisibleDeleteButton(){

        onData(anything()).inAdapterView(withId(R.id.photoGridView)).atPosition(0).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Delete")).check(matches(isDisplayed()));
    }

    @Test
    public void dialogShow(){
        onData(anything()).inAdapterView(withId(R.id.photoGridView)).atPosition(0).perform(click());


        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Delete")).check(matches(isDisplayed())).perform(click());
        onView(withText(R.string.delete_dialog_title)).inRoot(isDialog()).check(matches(isDisplayed()));
    }
}
