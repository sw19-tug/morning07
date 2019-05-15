package at.tugraz.morning07;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import java.io.File;
import java.util.ArrayList;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1;

    private ArrayList<File> photoFiles = new ArrayList<>();
    protected GridView photoGridView;
    protected PhotoAdapter photoAdapter;

    public static int width = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Get whether to show UI with rationale for requesting a permission or not.
            // Probably second request where user has the chance to check 'Never ask again'.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // TODO: Show asynchronous explanation to the user
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            // Permission has already been granted
            loadPhotosFromStorage();
            setupOpenPhotoActionButton(true);
        }

    }

    private void setupOpenPhotoActionButton(boolean enabled) {
        FloatingActionButton openPhotoActionButton = findViewById(R.id.openCameraActionButton);
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
        {
            openPhotoActionButton.setVisibility(View.VISIBLE);
            if (!enabled) {
                openPhotoActionButton.setAlpha(.4f);
                openPhotoActionButton.setEnabled(enabled);
            } else {
                openPhotoActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dispatchTakePictureIntent();
                    }
                });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPhotosFromStorage();
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

    ArrayList<File> getAllPhotoFiles() {
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
        return photoFiles;
    }

    void loadPhotosFromStorage() {
        photoFiles = getAllPhotoFiles();

        setupPhotoGridView();
    }

    void setupPhotoGridView()
    {

        width = Math.min(getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight());
        photoGridView = findViewById(R.id.photoGridView);
        photoGridView.setColumnWidth(width/4 - 8*5);
        photoAdapter = new PhotoAdapter(this, photoFiles);
        photoGridView.setAdapter(photoAdapter);
        photoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PhotoAdapter adapter = (PhotoAdapter) parent.getAdapter();
                String filepath = adapter.getPathFromItem(position);
                Intent intent = new Intent(MainActivity.this,BigImageActivity.class);
                intent.putExtra("filepath", filepath);
                startActivity(intent);
            }
        });
    }

    public PhotoAdapter getPhotoAdapter() {
        return photoAdapter;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadPhotosFromStorage();
                    setupOpenPhotoActionButton(true);
                } else {
                    // Permission denied
                    // Display empty grid view
                    setupOpenPhotoActionButton(false);
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
                photoAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
    private void dispatchTakePictureIntent() {

    }

}
