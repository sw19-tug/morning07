package at.tugraz.morning07;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.io.File;
import java.io.FileFilter;

public class MainActivity extends AppCompatActivity
{
    private static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 1;

    private File[] photoFiles;
    private String[] photoFilesPaths;
    protected GridView photoGridView;
    protected PhotoAdapter photoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Get whether to show UI with rationale for requesting a permission or not.
            // Probably second request where user has the chance to check 'Never ask again'.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // TODO: Show asynchronous explanation to the user
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
            }
        } else {
            // Permission has already been granted
            loadPhotosFromStorage();
        }

    }

    File[] getPhotoFiles() {
        File dirDownload = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (dirDownload.isDirectory()) {
             return dirDownload.listFiles(new FileFilter() {
                 @Override
                 public boolean accept(File file)
                 {
                     return (file.getAbsolutePath().endsWith(".bmp") ||
                             file.getAbsolutePath().endsWith(".gif") ||
                             file.getAbsolutePath().endsWith(".jpg") ||
                             file.getAbsolutePath().endsWith(".jpeg") ||
                             file.getAbsolutePath().endsWith(".png") ||
                             file.getAbsolutePath().endsWith(".webp"));
                 }
             });
        }
        return null;
    }

    String[] getPhotoFilesPaths(File[] files) {
        if (files == null) {
            return  null;
        }
        String[] filePaths = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            filePaths[i] = files[i].getAbsolutePath();
        }
        return filePaths;
    }

    void loadPhotosFromStorage() {
        photoFiles = getPhotoFiles();
        photoFilesPaths = getPhotoFilesPaths(photoFiles);

        setupPhotoGridView();
    }

    void setupPhotoGridView()
    {

        photoGridView = findViewById(R.id.photoGridView);
        photoAdapter = new PhotoAdapter(this, photoFilesPaths);
        photoGridView.setAdapter(photoAdapter);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_READ_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadPhotosFromStorage();
                } else {
                    // Permission denied
                    // Display empty grid view
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
