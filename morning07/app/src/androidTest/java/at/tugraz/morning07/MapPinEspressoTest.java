package at.tugraz.morning07;

import android.media.ExifInterface;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ImageView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class MapPinEspressoTest {

    @Rule
    public ActivityTestRule<BigImageActivity> activityRule = new ActivityTestRule<>(BigImageActivity.class);

    @Test
    public void MapPinButtonVisible() {

        File imgFile = activityRule.getActivity().imgFile;

        try {

            ExifInterface exifInterface = new ExifInterface(imgFile.getAbsolutePath());

            final String latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            final String longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);

            if (latitude != null && longitude != null) {
                onView(withId(R.id.showOnMapButton)).check(matches(isDisplayed()));
            }
            else{
                onView(withId(R.id.showOnMapButton)).check(matches(not(isDisplayed())));
            }

        } catch (Exception e) {

        }

    }

}

