package at.tugraz.morning07;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.shareButton = this.findViewById(R.id.shareButton);
        OnClickListenerShare shareListener = new OnClickListenerShare();
        ArrayList<Uri> imageUris = new ArrayList<>();
        imageUris.add(Uri.parse("Interner Speicher/Download/test.jpg"));
        shareListener.setImageArray(imageUris);
        this.shareButton.setOnClickListener(shareListener);
    }
}
