package at.tugraz.morning07;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class BigImageActivity extends AppCompatActivity implements View.OnClickListener {

    final int CROPPING = 1;

    enum MirrorDirection {
        Horizontal,
        Vertical
    }

    int turnRatio = 0;

    private Button shareButton;
    private Button saveButton;
    protected ImageView bigView;
    private Button mirrorHorizontalButton;
    private Button mirrorVerticalButton;
    private Button showOnMapButton;
    protected File imgFile;

    private boolean saveAsNewFile = false;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_image);

        this.bigView = (ImageView) findViewById(R.id.big_image);
        this.shareButton = this.findViewById(R.id.shareButton);
        this.saveButton = this.findViewById(R.id.saveButton);
        this.mirrorHorizontalButton = this.findViewById(R.id.mirrorHorizontalButton);
        this.mirrorVerticalButton = this.findViewById(R.id.mirrorVerticalButton);

        OnClickListenerShare shareListener = new OnClickListenerShare();
        ArrayList<Uri> imageUris = new ArrayList<>();
        File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
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

        this.shareButton = this.findViewById(R.id.shareButton);
        this.saveButton = this.findViewById(R.id.saveButton);
        this.mirrorHorizontalButton = this.findViewById(R.id.mirrorHorizontalButton);
        this.mirrorVerticalButton = this.findViewById(R.id.mirrorVerticalButton);

        this.showOnMapButton = this.findViewById(R.id.showOnMapButton);

        this.mirrorHorizontalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Bitmap bmp = ((BitmapDrawable)bigView.getDrawable()).getBitmap();
            bigView.setImageBitmap(getMirroredBitmap(bmp, MirrorDirection.Horizontal));
            }
        });

        this.mirrorVerticalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Bitmap bmp = ((BitmapDrawable)bigView.getDrawable()).getBitmap();
            bigView.setImageBitmap(getMirroredBitmap(bmp, MirrorDirection.Vertical));
            }
        });

        setUpMapButton();

    }

    private void setUpMapButton() {

        try {

            ExifInterface exifInterface = new ExifInterface(imgFile.getPath());

            float[] latLng = new float[2];
            if (exifInterface.getLatLong(latLng)) {

                final float[] location = latLng;

                this.showOnMapButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent showOnMapIntent = new Intent(BigImageActivity.this, MapActivity.class);
                        showOnMapIntent.putExtra("latitude", location[0]);
                        showOnMapIntent.putExtra("longitude", location[1]);
                        startActivity(showOnMapIntent);
                    }
                });

            } else {
                this.showOnMapButton.setVisibility(View.INVISIBLE);
            }

        } catch (Exception e) {
            System.out.println("[DEBUG SS] " + e.getLocalizedMessage());
        }

    }

    private Bitmap getMirroredBitmap(Bitmap bmp, MirrorDirection direction) {
        Matrix matrix = new Matrix();
        if (direction == MirrorDirection.Horizontal) {
            matrix.preScale(1.0f, -1.0f);
        } else {
            matrix.preScale(-1.0f, 1.0f);
        }
        if (saveButton.getVisibility() != View.VISIBLE) {
            saveButton.setVisibility(View.VISIBLE);
        }
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, false);
    }

    @Override
    public void onClick(View v) {
        crop();

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

        bigView.setClickable(true);
        bigView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(BigImageActivity.this, PopUp.class);
                intent.putExtra("filename", imgFile.getName());
                Date lastModi = new Date(imgFile.lastModified());
                intent.putExtra("date", lastModi.toString());
                intent.putExtra("size", String.valueOf(imgFile.length() / 1024));
                intent.putExtra("path", imgFile.getAbsolutePath());
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                intent.putExtra("resolution", myBitmap.getWidth() + " x " + myBitmap.getHeight());
                startActivity(intent);
                return true;
            }
        });
    }

    public void deletePicture() {
    final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    if(imgFile.exists())
                    {
                        boolean success = imgFile.delete();
                        if(success)
                        {
                            Intent intent = new Intent(BigImageActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "file could not be deleted!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    dialog.cancel();
                    break;
            }
        }
    };

    AlertDialog.Builder builder = new AlertDialog.Builder(BigImageActivity.this);
    builder.setTitle(R.string.delete_dialog_title).setMessage("Are you sure?").setNegativeButton("No", dialogClickListener)
            .setPositiveButton("Yes", dialogClickListener)
            .show();

    }

    public void turn()
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

        FileOutputStream fos;
        File newFile;
        if(saveAsNewFile) {
            String str = imgFile.getName();
            String[] arr = str.split("\\.");
            String filename = arr[0];
            for(int i = 1; i < arr.length - 1; i++)
                filename += arr[i];
            filename += "_" + System.currentTimeMillis() + "." + arr[arr.length - 1];
            newFile = new File(imgFile.getParent() + File.pathSeparator + filename);
            fos = new FileOutputStream(newFile);
        }
        else {
            fos = new FileOutputStream(imgFile);
        }
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
        saveAsNewFile = false;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        saveButton.setVisibility(View.INVISIBLE);
    }

    public void blackAndWhite()
    {
        saveButton.setVisibility(View.VISIBLE);
        BitmapDrawable source = (BitmapDrawable)bigView.getDrawable();
        Bitmap bm = Bitmap.createBitmap(source.getBitmap());
        bigView.setImageDrawable(new BitmapDrawable(toGreyScale(bm)));
        saveAsNewFile = true;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.big_image_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case(R.id.deleteButton):
                deletePicture();
                break;
            case(R.id.cropButton):
                crop();
                break;
            case(R.id.blackAndWhiteButton):
                blackAndWhite();
                break;
            case(R.id.turnButton):
                turn();
                break;
        }

        return true;
    }

    public void crop()
    {
        try
        {
            System.out.println("crop function");
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(Uri.fromFile(imgFile), "image/*");
            cropIntent.putExtra("crop", true);
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("return-data", true);

            startActivityForResult(cropIntent, CROPPING);
        }
        catch (Exception e) {
            String errorM = "your device does not support crop!";
            Toast toast = Toast.makeText(this, errorM, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.printf("onAcitvityResult %d", requestCode);
        if (resultCode == RESULT_OK)
        {
            System.out.println("Result OK");
            if (requestCode == CROPPING)
            {
                System.out.println("CROPPING");
                Bundle extras = data.getExtras();
                Bitmap cropped_pic = extras.getParcelable("data");
                bigView.setImageBitmap(cropped_pic);
                saveAsNewFile = true;
                try
                {
                    save(null);
                }
                catch (Exception e)
                {
                    String errorM = "Could not save file!";
                    System.out.printf("Exception: %s", e);
                    Toast toast = Toast.makeText(this, errorM, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }
}
