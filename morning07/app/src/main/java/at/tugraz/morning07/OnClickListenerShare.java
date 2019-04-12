package at.tugraz.morning07;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import java.util.ArrayList;

public class OnClickListenerShare implements View.OnClickListener {

    private ArrayList<Uri> imageUris = new ArrayList<Uri>();

    public OnClickListenerShare() {
        super();
    }

    @Override
    public void onClick(View view) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("image/*");
        view.getContext().startActivity(Intent.createChooser(shareIntent, "Share images to.."));
    }

    public void setImageArray(ArrayList<Uri> imageUris) {
        this.imageUris = imageUris;
    }
}
