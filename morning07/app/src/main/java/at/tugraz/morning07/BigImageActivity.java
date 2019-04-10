package at.tugraz.morning07;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class BigImageActivity extends AppCompatActivity {

    int turnRatio = 0;

    private Button shareButton;
    private Button saveButton;
    private ImageView bigView;
    private File imgFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_image);

        this.shareButton = this.findViewById(R.id.shareButton);
        this.saveButton = this.findViewById(R.id.saveButton);
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
        this.bigView = (ImageView) findViewById(R.id.big_image);
        Intent intent = getIntent();
        if(intent != null){
            String message = intent.getStringExtra("filenpath");
            if(message != null) {
                imgFile = new File(message);
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
        saveButton.setVisibility(View.VISIBLE);
        //ImageView bigView = (ImageView) findViewById(R.id.big_image);
        turnRatio+=90;
        bigView.setRotation(turnRatio == 360 ? 0 : turnRatio);
    }

    public void save(View view) throws Exception
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(bigView.getRotation());
        BitmapDrawable source = (BitmapDrawable)bigView.getDrawable();
        Bitmap bm = Bitmap.createBitmap(source.getBitmap(), 0 ,0,
                source.getBitmap().getWidth(), source.getBitmap().getHeight(), matrix, true);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 0, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = new FileOutputStream(imgFile);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
    }
}
