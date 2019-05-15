package at.tugraz.morning07;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BigImageActivity extends AppCompatActivity {

    int turnRatio = 0;

    private Button shareButton;
    private Button saveButton;
    private ImageView bigView;
    private File imgFile;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


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
            String message = intent.getStringExtra("filepath");
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
            String message = intent.getStringExtra("filepath");
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

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void save(View view) throws Exception
    {
        verifyStoragePermissions(this);
        Matrix matrix = new Matrix();
        matrix.postRotate(bigView.getRotation());
        BitmapDrawable source = (BitmapDrawable)bigView.getDrawable();
        Bitmap bm = Bitmap.createBitmap(source.getBitmap(), 0 ,0,
                source.getBitmap().getWidth(), source.getBitmap().getHeight(), matrix, true);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 0, bos);
        byte[] bitmapdata = bos.toByteArray();

        System.out.println("filepath: "+Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES));

        FileOutputStream fos = new FileOutputStream(imgFile);
        Context context = getApplicationContext();
        CharSequence text;
        try {
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            text = "Saved Successfully";

        }
        catch(IOException io) {
            text = "Error when Saving";
        }
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void blackAndWhite(View view) throws Exception
    {
        saveButton.setVisibility(View.VISIBLE);
        BitmapDrawable source = (BitmapDrawable)bigView.getDrawable();
        Bitmap bm = Bitmap.createBitmap(source.getBitmap());
        bigView.setImageDrawable(new BitmapDrawable(toGreyScale(bm)));
    }

    public static Bitmap toGreyScale(Bitmap src){
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());
        int A, R, G, B;
        int pixel;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                int gray = (R +  G + B) / 3;
                result.setPixel(x, y, Color.argb(A, gray, gray, gray));
            }
        }
        return result;
    }
}
