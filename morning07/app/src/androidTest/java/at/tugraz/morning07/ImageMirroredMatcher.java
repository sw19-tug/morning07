package at.tugraz.morning07;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.Description;

public class ImageMirroredMatcher extends TypeSafeMatcher<View> {

    private Bitmap mirrored;

    public ImageMirroredMatcher(Bitmap mirrored) {
        super(View.class);
        this.mirrored = mirrored;
    }

    @Override public boolean matchesSafely(View view) {

        if (!(view instanceof ImageView)){
            return false;
        }

        ImageView imageView = (ImageView)view;
        Bitmap original = ((BitmapDrawable)(imageView.getDrawable())).getBitmap();

        boolean is_mirrored = true;
        for (int x = 0; x < original.getWidth() && is_mirrored; x++) {
            for (int y = 0; y < original.getHeight() && is_mirrored; y++) {
                int color = original.getPixel(x, y);
                int color_mirrored = mirrored.getPixel(x, y);
                is_mirrored = color == color_mirrored;
            }
        }
        return is_mirrored;
    }

    @Override public void describeTo(Description description) {
        description.appendText("with mirrored bitmap:").appendValue(mirrored);
    }
}