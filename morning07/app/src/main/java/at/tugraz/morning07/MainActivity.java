package at.tugraz.morning07;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;
import android.net.Uri;
import android.widget.Button;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.widget.ImageView;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import at.tugraz.morning07.R;

public class MainActivity extends AppCompatActivity
{
    private static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 1;

    private File[] photoFiles;
    private String[] photoFilesPaths;
    protected GridView photoGridView;
    protected PhotoAdapter photoAdapter;

    public static int width = 0;

    private Button shareButton;

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

    private boolean isImageFile(File file) {
        return (file.getAbsolutePath().endsWith(".bmp") ||
                file.getAbsolutePath().endsWith(".gif") ||
                file.getAbsolutePath().endsWith(".jpg") ||
                file.getAbsolutePath().endsWith(".jpeg") ||
                file.getAbsolutePath().endsWith(".png") ||
                file.getAbsolutePath().endsWith(".webp"));
    }

    public ArrayList<File> getPhotoFiles(File dir) {
        ArrayList<File> files = new ArrayList<File>();
        if (dir != null && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    files.addAll(getPhotoFiles(file));
                } else if (isImageFile(file)) {
                    files.add(file);
                }
            }
        } else if (isImageFile(dir)) {
            files.add(dir);
        }
        return files;
    }

    File[] getAllPhotoFiles() {
        String[] directories = {
                Environment.DIRECTORY_DCIM,
                Environment.DIRECTORY_PICTURES
        };
        ArrayList<File> photoFiles = new ArrayList<File>();
        for (int directoryIndex = 0; directoryIndex < directories.length; directoryIndex++) {
            String directoryName = directories[directoryIndex];
            File directory = Environment.getExternalStoragePublicDirectory(directoryName);
            photoFiles.addAll(getPhotoFiles(directory));
        }
        return photoFiles.toArray(new File[photoFiles.size()]);
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
        photoFiles = getAllPhotoFiles();
        photoFilesPaths = getPhotoFilesPaths(photoFiles);

        setupPhotoGridView();
    }

    void setupPhotoGridView()
    {

        width = Math.min(getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight());
        photoGridView = findViewById(R.id.photoGridView);
        photoGridView.setColumnWidth(width/4 - 8*5);
        photoAdapter = new PhotoAdapter(this, photoFilesPaths);
        photoGridView.setAdapter(photoAdapter);
        photoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PhotoAdapter adapter = (PhotoAdapter) parent.getAdapter();
                String filepath = adapter.getItem(position);
                File photo = new File(filepath);
                Intent intent = new Intent(MainActivity.this,BigImageActivity.class);
                intent.putExtra("filenpath", filepath);
                startActivity(intent);
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search_images);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // use Adapter
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}