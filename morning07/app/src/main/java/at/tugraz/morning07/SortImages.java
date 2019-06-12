package at.tugraz.morning07;

import java.io.File;
import java.util.ArrayList;

public class SortImages {

    public SortImages() {
    }

    public ArrayList<File> sortByName(ArrayList<File> imageArrayToSort)
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

    public ArrayList<File> sortByDate(ArrayList<File> imageArrayToSort)
    {
        for (int i = 0; i<imageArrayToSort.size() - 1; i++)
        {
            for (int next = i+1; next < imageArrayToSort.size(); next++)
            {
                if (imageArrayToSort.get(i).lastModified() < imageArrayToSort.get(next).lastModified())
                {
                    File tmp = imageArrayToSort.get(i);
                    imageArrayToSort.set(i, imageArrayToSort.get(next));
                    imageArrayToSort.set(next, tmp);
                }
            }
        }
        return imageArrayToSort;
    }

    public ArrayList<File> sortByFileSize(ArrayList<File> imageArrayToSort)
    {
        for (int i = 0; i<imageArrayToSort.size() - 1; i++)
        {
            for (int next = i+1; next < imageArrayToSort.size(); next++)
            {
                if (imageArrayToSort.get(i).length() < imageArrayToSort.get(next).length())
                {
                    File tmp = imageArrayToSort.get(i);
                    imageArrayToSort.set(i, imageArrayToSort.get(next));
                    imageArrayToSort.set(next, tmp);
                }
            }
        }
        return imageArrayToSort;
    }
}
