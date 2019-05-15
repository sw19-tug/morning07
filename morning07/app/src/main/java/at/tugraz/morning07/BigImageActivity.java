package at.tugraz.morning07;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class BigImageActivity extends AppCompatActivity {

    private Button shareButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_image);

        this.shareButton = this.findViewById(R.id.shareButton);
        OnClickListenerShare shareListener = new OnClickListenerShare();
        ArrayList<Uri> imageUris = new ArrayList<>();
        File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Intent intent = getIntent();
        String message = intent.getStringExtra("filenpath");

        final File imgFile = new  File(message);
        imageUris.add(Uri.parse(imgFile.getAbsolutePath()));
        shareListener.setImageArray(imageUris);
        this.shareButton.setOnClickListener(shareListener);

        this.deleteButton = this.findViewById(R.id.deleteButton);



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
    protected void onStart() {
        super.onStart();
        ImageView bigView = (ImageView) findViewById(R.id.big_image);
        Intent intent = getIntent();
        String message = intent.getStringExtra("filenpath");

        File imgFile = new  File(message);

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            bigView.setImageBitmap(myBitmap);


        }
    }
}
