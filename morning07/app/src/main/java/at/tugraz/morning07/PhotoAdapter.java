package at.tugraz.morning07;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.widget.GridView.AUTO_FIT;

public class PhotoAdapter extends BaseAdapter
{
    private Context mContext;
    private ArrayList<String> files_;
    private int width_ = 0;

    public PhotoAdapter(Context c, ArrayList<String> files)
    {
        mContext = c;
        files_ = files;
        width_ = MainActivity.width;
    }

    public int getCount() {
        return files_.size();
    }

    public String getItem(int position) {
        return files_.get(position);
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

        Bitmap myBitmap = BitmapFactory.decodeFile(files_.get(position));
        imageView.setImageBitmap(myBitmap);
        return imageView;
    }
}
