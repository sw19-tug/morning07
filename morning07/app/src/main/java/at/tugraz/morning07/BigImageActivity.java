package at.tugraz.morning07;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class BigImageActivity extends AppCompatActivity {

    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_image);

        this.shareButton = this.findViewById(R.id.shareButton);
        OnClickListenerShare shareListener = new OnClickListenerShare();
        ArrayList<Uri> imageUris = new ArrayList<>();
        File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        imageUris.add(Uri.parse(f.getAbsolutePath() + "/test.jpg"));
        shareListener.setImageArray(imageUris);
        this.shareButton.setOnClickListener(shareListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageView bigView = (ImageView) findViewById(R.id.big_image);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            byte[] byteArray = extras.getByteArray("bitmap");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            bigView.setImageBitmap(bmp);
        }
    }
}
