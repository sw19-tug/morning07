package at.tugraz.morning07;

import android.media.Image;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
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
    FileMock image1 = new FileMock("asdf", "Fabian", new Date(), 1000);
    FileMock image2 = new FileMock("jkl", "Max", new Date(), 100);
    FileMock image3 = new FileMock("foo", "Jakob", new Date(), 300000);
    //ToDo sorting arrays
    FileMock[] imageArray = {image1, image2, image3};
    FileMock[] sortedbyNameArray = {image2, image1, image3};
    FileMock[] sortedbyDateArray = {image3, image2, image1};
    FileMock[] sortedbyFileSizeArray = {image3, image2, image1};

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
