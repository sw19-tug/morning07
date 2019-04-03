package at.tugraz.morning07;

import java.io.File;
import java.util.ArrayList;

import android.net.Uri;
import android.os.Environment;
import android.widget.Button;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        byte[] byteArray = extras.getByteArray("bitmap");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        bigView.setImageBitmap(bmp);
    }
}
