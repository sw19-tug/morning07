package at.tugraz.morning07;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends BaseAdapter implements Filterable
{
    private Context mContext;
    private final ArrayList<File> originalImageList;
    private ArrayList<File> filteredImageList;
    public List selectedPositions;

    private int width_ = 0;

    public PhotoAdapter(Context c, ArrayList<File> files)
    {
        mContext = c;
        originalImageList = new ArrayList<>(files);
        filteredImageList = files;
        width_ = MainActivity.width;
        selectedPositions = new ArrayList<>();
    }

    public int getCount() {
        return filteredImageList.size();
    }

    public File getItem(int position) {
        return filteredImageList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if(width_ == 0)
            width_ = Math.min(parent.getMeasuredWidth(), parent.getMeasuredHeight());

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(width_/4 - 4 * 5 ,width_/4));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else
        {
            imageView = (ImageView) convertView;
        }

        Bitmap myBitmap = BitmapFactory.decodeFile(getPathFromItem(position));
        imageView.setImageBitmap(myBitmap);
        return imageView;
    }

    public String getPathFromItem(int position) {
        return filteredImageList.get(position).getAbsolutePath();
    }

    public ArrayList<File> getFilteredImageList() {
        return filteredImageList;
    }

    public ArrayList<File> getOriginalImageList() {
        return originalImageList;
    }

    @Override
    public Filter getFilter() {
        return imageFilter;
    }


    private Filter imageFilter = new Filter() {

        @VisibleForTesting
        public ArrayList<File> testPerformFiltering(CharSequence constraint) {
            return (ArrayList<File>) this.performFiltering(constraint).values;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<File> tmpList = new ArrayList<>();

                for (File file : originalImageList) {
                    if(file.getName().toLowerCase().trim().contains(constraint.toString().toLowerCase().trim())) {
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
            filteredImageList.clear();
            filteredImageList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
