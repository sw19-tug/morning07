package at.tugraz.morning07;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SortUnitTest{

    SortImages sort;
    FileMock image3 = new FileMock("foo", "Jakob", new Date(), 3000);
    FileMock image1 = new FileMock("asdf", "Fabian", new Date(), 1000);
    FileMock image2 = new FileMock("xkl", "Max", new Date(), 2000);

    //ToDo sorting arrays
    ArrayList<File> imageList;
    ArrayList<File>  sortedbyNameList;
    ArrayList<File>  sortedbyDateList;
    ArrayList<File> sortedbyFileSizeList;

    @Before
    public void setUp() throws IOException {
        sort = new SortImages();
        imageList = new ArrayList<>();
        sortedbyNameList = new ArrayList<>();
        sortedbyDateList = new ArrayList<>();
        sortedbyFileSizeList = new ArrayList<>();

        image3.setLastModified(100);
        image1.setLastModified(103);
        image2.setLastModified(104);

        imageList.add(image1);
        imageList.add(image2);
        imageList.add(image3);

        sortedbyNameList.add(image1);
        sortedbyNameList.add(image3);
        sortedbyNameList.add(image2);

        sortedbyDateList.add(image2);
        sortedbyDateList.add(image1);
        sortedbyDateList.add(image3);


        sortedbyFileSizeList.add(image3);
        sortedbyFileSizeList.add(image2);
        sortedbyFileSizeList.add(image1);
    }

    @Test
    public void sortByName(){
        assertNotNull(sort);
        assertEquals(sortedbyNameList, sort.sortByName(imageList));
    }

    @Test
    public void sortByDate(){
        assertNotNull(sort);
        assertEquals(sortedbyDateList, sort.sortByDate(imageList));
    }

    @Test
    public void sortByFileSize(){
        assertNotNull(sort);
        assertEquals(sortedbyFileSizeList, sort.sortByFileSize(imageList));
    }
}
