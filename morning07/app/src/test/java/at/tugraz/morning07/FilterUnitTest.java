package at.tugraz.morning07;

import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.media.Image;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;



public class FilterUnitTest{

    FilterImages filter;
    Image image1;
    Image image2;
    Image image3;
    Image[] imageArray = {image1, image2, image3};

    @Before
    public void setUp() throws IOException {
        filter = new FilterImages();
    }

    @Test
    public void filterByName(){
        assertNotNull(filter);
        assertTrue(filter.filterByName());
    }

    @Test
    public void filterByDate(){
        assertNotNull(filter);
        assertTrue(filter.filterByDate());
    }

    @Test
    public void filterByFileSize(){
        assertNotNull(filter);
        assertTrue(filter.filterByFileSize());
    }
}
