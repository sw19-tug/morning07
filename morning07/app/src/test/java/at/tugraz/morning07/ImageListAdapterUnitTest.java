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

import android.widget.Filter;

public class ImageListAdapterUnitTest {

    ImageListAdapter adapter;
    ArrayList<File> imageList;
    ArrayList<File> filteredImageList;
    int imageCounter;
    CharSequence query;

    FileMock image3 = new FileMock("foo", "Jakob", new Date(), 3000);
    FileMock image1 = new FileMock("asdf", "Fabian", new Date(), 1000);
    FileMock image2 = new FileMock("xkl", "Max", new Date(), 2000);

    @Before
    public void setUp() throws IOException {
        imageList = new ArrayList<>();
        filteredImageList = new ArrayList<>();

        imageList.add(image1);
        imageList.add(image2);
        imageList.add(image3);

        filteredImageList.add(image2);

        imageCounter = imageList.size();
        query = "asdf";
        adapter = new ImageListAdapter(null, imageList);
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
        assertEquals(image2, adapter.getItem(2));
    }

    @Test
    public void getFilter(){
        assertNotNull(adapter.getFilter());
    }

    @Test
    public void filterImages() {
        assertEquals(filteredImageList, adapter.getFilter().performeFiltering(query).values);
        assertEquals(imageCounter, adapter.getFilter().performeFiltering(query).count);
    }

    @Test
    public void finalTest() {
        assertNotNull(adapter);
        assertNotNull(adapter.getFilter());
        FilterResults result = adapter.getFilter().performeFiltering(query);
        assertEquals(filteredImageList, result.values);
        assertEquals(imageCounter, result.count);
        CharSequence nullQuery = "fffNULLfff";
        result = adapter.getFilter().performeFiltering(nullQuery);
        assertEquals(new ArrayList<File>(), result.value);
        assertEquals(0, result.count);
        CharSequence allQuery = "";
        assertEquals(imageList, result.value);
        assertEquals(imageCounter, result.count);
    }
}
