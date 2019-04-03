package at.tugraz.morning07;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class Overview_stub extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view_stub);
    }

    public void test(View v){
        Intent intent = new Intent(this, MainActivity.class);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.prev3);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        intent.putExtra("bitmap",byteArray);
        startActivity(intent);
    }
}
