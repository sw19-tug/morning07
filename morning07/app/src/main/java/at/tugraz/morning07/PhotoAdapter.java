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

public class PhotoAdapter extends BaseAdapter
{
    int mGalleryItemBackground;
    private Context mContext;
    private Image[] images;
    private String[] files_;


    public PhotoAdapter(Context c, String[] files)
    {
        mContext = c;
        files_ = files;
    }

    public int getCount() {
        return files_.length;
    }

    public Image getItem(int position) {
        return images[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(400, 400));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }

        System.out.println("DEBUG MS " + mContext.getContentResolver());

        Bitmap myBitmap = BitmapFactory.decodeFile(files_[position]);
        imageView.setImageBitmap(myBitmap);
        return imageView;
    }
}
