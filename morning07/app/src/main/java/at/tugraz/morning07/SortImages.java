package at.tugraz.morning07;

import android.media.Image;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Filter;

public class SortImages {

    public SortImages() {
    }

    public List<File> sortByName(List<File> imageArrayToSort)
    {
        for (int i = 0; i<imageArrayToSort.size() - 1; i++)
        {
            for (int next = i+1; next < imageArrayToSort.size(); next++)
            {
                if (imageArrayToSort.get(i).getName().compareTo(imageArrayToSort.get(next).getName()) > 0)
                {
                    File tmp = imageArrayToSort.get(i);
                    imageArrayToSort.set(i, imageArrayToSort.get(next));
                    imageArrayToSort.set(next, tmp);
                }
            }
        }

        return imageArrayToSort;
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
