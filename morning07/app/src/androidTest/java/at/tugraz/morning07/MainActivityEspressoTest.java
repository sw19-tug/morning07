package at.tugraz.morning07;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.GridView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest
{
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void hasVisibleGridView()
    {
        onView(withId(R.id.photoGridView)).check(matches(isDisplayed()));
    }

    @Test
    public void checkForPhotoAdapter()
    {
        assertThat(activityRule.getActivity().photoAdapter, instanceOf(PhotoAdapter.class));
    }

    @Test
    public void checkGridViewPhotoAdapter()
    {
        GridView photoGridView = activityRule.getActivity().photoGridView;
        assertThat(photoGridView.getAdapter(), instanceOf(PhotoAdapter.class));
    }


    @Test
    public void checkFiles()
    {
        ArrayList<File> photoFiles = activityRule.getActivity().getAllPhotoFiles();
        assertThat("Photo files must not be null.", photoFiles != null);
    }

    @Test
    public void checkSearch()
    {
        String test_query = "asdf";
        ArrayList<File> photoFiles = activityRule.getActivity().getAllPhotoFiles();

        onView(withId(R.id.search_images)).perform(click()).perform(typeText(test_query));
        for (File f : activityRule.getActivity().getPhotoAdapter().getFilteredImageList())
        {
            assertTrue(f.getName().toLowerCase().trim().contains(test_query.toLowerCase().trim()));
        }
    }
}