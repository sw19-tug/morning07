package at.tugraz.morning07;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.GridView;
import android.widget.ListAdapter;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.instanceOf;
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
        File[] photoFiles = activityRule.getActivity().getPhotoFiles();
        assertThat("Photo files must not be null.", photoFiles != null);
    }

    @Test
    public void checkFilePaths()
    {
        File[] photoFiles = activityRule.getActivity().getPhotoFiles();
        String[] photoFilesPaths = activityRule.getActivity().getPhotoFilesPaths(photoFiles);
        assertThat("Photo file paths must not be null.", photoFilesPaths != null);
    }

    @Test
    public void checkFilePathsIfImages()
    {
        File[] photoFiles = activityRule.getActivity().getPhotoFiles();
        String[] photoFilesPaths = activityRule.getActivity().getPhotoFilesPaths(photoFiles);

        for(int i = 0; i < photoFilesPaths.length; i++)
        {
            assertThat("Photo file paths must end with valid endings",
                    photoFilesPaths[i].endsWith(".gif") ||
                    photoFilesPaths[i].endsWith(".jpg") ||
                    photoFilesPaths[i].endsWith(".jpeg") ||
                    photoFilesPaths[i].endsWith(".png") ||
                    photoFilesPaths[i].endsWith(".webp"));

        }
    }
}