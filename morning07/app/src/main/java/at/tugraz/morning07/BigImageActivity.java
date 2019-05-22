package at.tugraz.morning07;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BigImageActivity extends AppCompatActivity implements View.OnClickListener {

    final int CROPPING = 1;

    int turnRatio = 0;

    private Button shareButton;
    private Button deleteButton;
    private Button saveButton;
    private Button cropButton;
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
        this.deleteButton = this.findViewById(R.id.deleteButton);
        this.cropButton = this.findViewById(R.id.cropButton);
        cropButton.setOnClickListener(this);

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

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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

        });
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
        catch (ActivityNotFoundException anfe) {
            String errorM = "your device does not support crop!";
            Toast toast = Toast.makeText(this, errorM, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.printf("onAcitvityResult %d", requestCode);
        if (resultCode == RESULT_OK) {
            System.out.println("Result OK");
            if (requestCode == CROPPING) {
                System.out.println("CROPPING");
                Bundle extras = data.getExtras();
                Bitmap cropped_pic = extras.getParcelable("data");
                bigView.setImageBitmap(cropped_pic);
            }
        }
    }
}
