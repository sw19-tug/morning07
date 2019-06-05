package at.tugraz.morning07;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class PopUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.7));

        Intent intent = getIntent();
        TextView tv_filename = findViewById(R.id.filename);
        TextView tv_date = findViewById(R.id.date);
        TextView tv_size = findViewById(R.id.size);
        TextView tv_path = findViewById(R.id.path);
        TextView tv_resolution = findViewById(R.id.resolution);
        String filename_text = "Filename: " + intent.getStringExtra("filename");
        String date_text = "Date: " + intent.getStringExtra("date");
        String size_text = "Size: " + intent.getStringExtra("size") + " kb";
        String path_text = "Path: " + intent.getStringExtra("path");
        String resolution_text = "Resolution: " + intent.getStringExtra("resolution");
        tv_filename.setText(filename_text);
        tv_date.setText(date_text);
        tv_size.setText(size_text);
        tv_path.setText(path_text);
        tv_resolution.setText(resolution_text);
    }
}
