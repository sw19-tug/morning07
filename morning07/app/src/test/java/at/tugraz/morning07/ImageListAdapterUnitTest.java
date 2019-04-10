package at.tugraz.morning07;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import at.tugraz.morning07.adapter.ImageListAdapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ImageListAdapterUnitTest{

    ImageListAdapter adapter;
    ArrayList<File> imageList;
    ArrayList<File> filteredImageList;
    ImageListAdapter.ImageFilter filter;
    int imageCounter;
    CharSequence query;

    FileMock image3 = new FileMock("foo", "Jakob", new Date(), 3000);
    FileMock image1 = new FileMock("asdf", "Fabian", new Date(), 1000);
    FileMock image2 = new FileMock("xkl", "Max", new Date(), 2000);

    @Before
    public void setUp() throws IOException {
        imageList = new ArrayList<>();
        filteredImageList = new ArrayList<>();
        adapter = new ImageListAdapter(null, imageList);
        filter = (ImageListAdapter.ImageFilter) adapter.getFilter();
        imageList.add(image1);
        imageList.add(image2);
        imageList.add(image3);

        filteredImageList.add(image2);

        imageCounter = imageList.size();


    }

    @Test
    public void doesClassExist(){
        assertNotNull(adapter);

    }

    @Test
    public void getCount(){
        assertEquals(imageCounter, adapter.getCount());
    }

    @Test
    public void getItem(){
        assertEquals(image2, adapter.getItem(1));
    }

    @Test
    public void getFilter(){
        assertNotNull(adapter.getFilter());
    }

    @Test
    public void filterImages() {
        query = "Max";
        assertEquals(filteredImageList, filter.testPerformFiltering(query));
        assertEquals(filteredImageList.size(), filter.testPerformFiltering(query).size());
    }

    @Test
    public void finalTest() {
        assertNotNull(adapter);
        assertNotNull(adapter.getFilter());
        query = "Max";
        ArrayList<File> result = filter.testPerformFiltering(query);
        assertEquals(filteredImageList, result);
        assertEquals(filteredImageList.size(), result.size());
        CharSequence nullQuery = "fffNULLfff";
        result = filter.testPerformFiltering(nullQuery);
        assertEquals(new ArrayList<File>(), result);
        assertEquals(0, result.size());
        CharSequence allQuery = "";
        result = filter.testPerformFiltering(allQuery);
        assertEquals(imageList, result);
        assertEquals(imageCounter, result.size());
    }
}
