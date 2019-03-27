package at.tugraz.morning07;

import android.media.Image;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;



public class SortUnitTest{

    SortImages sort;
    Image image1;
    Image image2;
    Image image3;
    Image[] imageArray = {image1, image2, image3};
    Image[] sortedbyNameArray = {image3, image2, image1};

    @Before
    public void setUp() throws IOException {
        sort = new SortImages();
    }

    @Test
    public void sortByName(){
        assertNotNull(sort);
        assertEquals(sortedbyNameArray, sort.sortByName(imageArray));
    }

    @Test
    public void sortByDate(){
        assertNotNull(sort);
        assertTrue(sort.sortByDate());
    }

    @Test
    public void sortByFileSize(){
        assertNotNull(sort);
        assertTrue(sort.sortByFileSize());
    }
}
