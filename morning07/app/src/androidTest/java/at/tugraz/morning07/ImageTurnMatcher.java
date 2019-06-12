package at.tugraz.morning07;

import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ImageTurnMatcher extends TypeSafeMatcher<View> {

private final float expectedRotation;

public ImageTurnMatcher(float expectedRotation) {
        super(View.class);
        this.expectedRotation = expectedRotation;
}

@Override
protected boolean matchesSafely(View target) {
        if (!(target instanceof ImageView)){
        return false;
        }
        ImageView imageView = (ImageView) target;
        return imageView.getRotation() == expectedRotation;
}


@Override
public void describeTo(Description description) {
        description.appendText("with rotation: ");
        description.appendValue(expectedRotation);
        }
}