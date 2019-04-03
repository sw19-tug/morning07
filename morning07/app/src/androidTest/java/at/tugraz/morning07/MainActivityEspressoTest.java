package at.tugraz.morning07;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest
{
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void listViewVisible()
    {
        onView(withId(R.id.lv_main)).check(matches(isDisplayed()));
    }

    @Test
    public void checkFileName()
    {
        String[] myName = activityRule.getActivity().filename_;

        for(int i = 0; i < myName.length; i++)
        {
            String testString = myName[i];
            assertThat(testString.isEmpty(), is(true));
        }
    }

    @Test
    public void checkFilePath()
    {
       String[] myPath = activityRule.getActivity().filepath_;

        for(int i = 0; i < myPath.length; i++)
        {
            String testString = myPath[i];
            assertThat(testString.isEmpty(), is(true));
        }
    }
}