package at.tugraz.morning07;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;

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

    public static Matcher<View> isMirrored(Bitmap mirrored) {
        return new ImageMirroredMatcher(mirrored);
    }

    @Test
    public void testMirrorHorizontalExecuted() {

        ImageView bigView = activityRule.getActivity().bigView;
        Bitmap original = ((BitmapDrawable)bigView.getDrawable()).getBitmap();
        Bitmap mirrored_original = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());

        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                int color = original.getPixel(x, y);
                int x_mirrored = x;
                int y_mirrored = original.getHeight() - 1 - y;
                mirrored_original.setPixel(x_mirrored, y_mirrored, color);
            }
        }

        onView(withId(R.id.mirrorHorizontalButton)).perform(click());
        onView(withId(R.id.big_image)).check(matches(isMirrored(mirrored_original)));
    }

    @Test
    public void testMirrorVerticalExecuted() {

        ImageView bigView = activityRule.getActivity().bigView;
        Bitmap original = ((BitmapDrawable)bigView.getDrawable()).getBitmap();
        Bitmap mirrored_original = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());

        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                int color = original.getPixel(x, y);
                int x_mirrored = original.getWidth() - 1 - x;
                int y_mirrored = y;
                mirrored_original.setPixel(x_mirrored, y_mirrored, color);
            }
        }

        onView(withId(R.id.mirrorHorizontalButton)).perform(click());
        onView(withId(R.id.big_image)).check(matches(isMirrored(mirrored_original)));
    }
}
