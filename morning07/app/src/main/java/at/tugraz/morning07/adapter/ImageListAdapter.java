package at.tugraz.morning07.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.io.File;
import java.util.ArrayList;

public class ImageListAdapter extends BaseAdapter implements Filterable {

    private ImageFilter imageFilter;
    private ArrayList<File> originalImageList;
    private ArrayList<File> filteredImageList;
    private Activity activity;

    public ImageListAdapter(Activity activity, ArrayList<File> imageList) {
        this.activity = activity;
        this.originalImageList = imageList;
        this.filteredImageList = imageList;

        getFilter();
    }

    @Override
    public int getCount() {
        return filteredImageList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredImageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO: implement getView
        return null;
    }

    @Override
    public Filter getFilter() {
        if(imageFilter == null) {
            imageFilter = new ImageFilter();
        }

        return imageFilter;
    }


    private class ImageFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<File> tmpList = new ArrayList<File>();

                for (File file : originalImageList) {
                    if(file.getName().toLowerCase().equals(constraint.toString().toLowerCase())) {
                        tmpList.add(file);
                    }
                }

                filterResults.count = tmpList.size();
                filterResults.values = tmpList;
            } else {
                filterResults.count = originalImageList.size();
                filterResults.values = originalImageList;
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results == null || results.count <= 0) {
                return;
            }
            filteredImageList = (ArrayList<File>) results.values;
            notifyDataSetChanged();
        }
    }
}
