package at.tugraz.morning07;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class PhotoAdapterUnitTest {

    PhotoAdapter adapter;
    ArrayList<File> imageList;
    int imageCounter;
    FileMock image3 = new FileMock("foo", "Jakob", new Date(), 3000);
    FileMock image1 = new FileMock("asdf", "Fabian", new Date(), 1000);
    FileMock image2 = new FileMock("xkl", "Max", new Date(), 2000);

    @Before
    public void setUp() throws IOException {
        imageList = new ArrayList<>();
        adapter = new PhotoAdapter(null, imageList);
        imageList.add(image1);
        imageList.add(image2);
        imageList.add(image3);

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
}
