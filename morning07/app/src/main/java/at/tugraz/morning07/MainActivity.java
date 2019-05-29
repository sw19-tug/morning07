package at.tugraz.morning07;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.icu.text.Normalizer2;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import java.io.File;
import java.util.ArrayList;
import android.net.Uri;
import android.widget.Button;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends AppCompatActivity
{
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1;

    private static final int REQUEST_PERMISSION_IMAGE_CAPTURE = 2;

    private ArrayList<File> photoFiles = new ArrayList<>();
    protected GridView photoGridView;
    protected PhotoAdapter photoAdapter;

    String takenPhotoPath;

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
            File directory = getExternalStoragePublicDirectory(directoryName);
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
        photoGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        photoGridView.setMultiChoiceModeListener(new GridView.MultiChoiceModeListener(){

            ArrayList<ImageView> iv_list = new ArrayList<>();
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                int selectCount = photoGridView.getCheckedItemCount();


                switch (selectCount) {
                    case 1:
                        mode.setSubtitle("One item selected");
                        break;
                    default:
                        mode.setSubtitle("" + selectCount + " items selected");
                        break;
                }

                if(checked){
                    ImageView tv = (ImageView) photoGridView.getChildAt(position);
                    tv.setColorFilter(getResources().getColor(R.color.colorHighlighted));
                    iv_list.add(tv);

                }else{
                    ImageView tv = (ImageView) photoGridView.getChildAt(position);
                    tv.setColorFilter(Color.TRANSPARENT);
                    iv_list.remove(tv);
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater =  mode.getMenuInflater();
                menuInflater.inflate(R.menu.bulk_menu,menu);
                mode.setTitle("Images Selected");
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Respond to clicks on the actions in the CAB
                switch (item.getItemId()) {
                    case R.id.deleteButton:
                        //Get your menu item from the id
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                for(ImageView tv : iv_list)
                {
                    tv.setColorFilter(Color.TRANSPARENT);
                }

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
    
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        takenPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PERMISSION_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            // Save taken picture to system gallery
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(takenPhotoPath);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);

            // TODO: Update grid view
        }
    }



    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(
                        this,
                        getApplicationContext().getPackageName() + ".provider",
                        photoFile
                );
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_PERMISSION_IMAGE_CAPTURE);
            }
        }
    }

}
