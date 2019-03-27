package at.tugraz.morning07;

import android.media.Image;
import android.provider.MediaStore;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Filter;

public class SortImages {

    public SortImages() {
    }

    public Image[] sortByName(Image[] imageArrayToSort)
    {
        Image[] tmp_array = imageArrayToSort;
        tmp_array[0] = imageArrayToSort[2];
        tmp_array[1] = imageArrayToSort[1];
        tmp_array[2] = imageArrayToSort[0];
        return tmp_array;
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
