package at.tugraz.morning07;

import android.provider.MediaStore;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Filter;

public class FilterImages {

    public List<MediaStore.Images> imageList;

    public FilterImages() {

    }

    public boolean filterByName(){
        return true;
    }
}
