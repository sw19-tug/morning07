package at.tugraz.morning07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.MapView;

public class MapActivity extends AppCompatActivity {

    protected MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = this.findViewById(R.id.mapView);
    }
}
