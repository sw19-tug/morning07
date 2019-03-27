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
    Image[] sortedbyDateArray = {image2, image3, image1};
    Image[] sortedbyFileSizeArray = {image3, image1, image2};

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
        assertEquals(sortedbyDateArray, sort.sortByDate(imageArray));
    }

    @Test
    public void sortByFileSize(){
        assertNotNull(sort);
        assertEquals(sortedbyFileSizeArray, sort.sortByFileSize(imageArray));
    }
}
