package at.tugraz.morning07;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
        Intent intent = getIntent();
        if(intent != null){
            String message = intent.getStringExtra("filenpath");
            if(message != null) {
                File imgFile = new File(message);
                imageUris.add(Uri.parse(imgFile.getAbsolutePath()));
                shareListener.setImageArray(imageUris);
                this.shareButton.setOnClickListener(shareListener);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageView bigView = (ImageView) findViewById(R.id.big_image);
        Intent intent = getIntent();
        if(intent != null){
            String message = intent.getStringExtra("filenpath");
            if(message != null) {
                File imgFile = new File(message);
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                bigView.setImageBitmap(myBitmap);
            }
        }
        else{
            bigView.setImageResource(R.drawable.prev2);
        }
    }

    public void turn(View view)
    {
        ImageView bigView = (ImageView) findViewById(R.id.big_image);
        bigView.setRotation(90);
    }
}
