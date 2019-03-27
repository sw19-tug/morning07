package at.tugraz.morning07;

import android.provider.MediaStore;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Filter;

public class SortImages {

    public List<MediaStore.Images> imageList;

    public SortImages() {

    }

    public boolean sortByName()
    {
        return true;
    }

    public boolean sortByDate()
    {
        return true;
    }

    public boolean sortByFileSize()
    {
        return true;
    }
}
