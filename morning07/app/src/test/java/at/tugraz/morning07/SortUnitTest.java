package at.tugraz.morning07;

import android.media.Image;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;



public class SortUnitTest{

    SortImages sort;
    MockImage image1 = new MockImage("cat", new Date(2016, 11, 25,20,59), 20);
    MockImage image2 = new MockImage("adolf", new Date(2016, 11, 25,20,58), 29);
    MockImage image3 = new MockImage("fabian", new Date(2016, 11, 25,18,58), 60);
    MockImage[] imageArray = {image1, image2, image3};
    MockImage[] sortedbyNameArray = {image2, image1, image3};
    MockImage[] sortedbyDateArray = {image3, image2, image1};
    MockImage[] sortedbyFileSizeArray = {image3, image2, image1};

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
