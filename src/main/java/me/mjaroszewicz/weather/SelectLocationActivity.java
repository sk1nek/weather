package me.mjaroszewicz.weather;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SelectLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private int ACTION_RESULT = 12321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Toast.makeText(this, "Hold click on your location for a second. ", Toast.LENGTH_LONG).show();

        final Intent resultIntent = new Intent();

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {


                resultIntent.putExtra("lat", latLng.latitude);
                resultIntent.putExtra("lng", latLng.longitude);

                setResult(ACTION_RESULT, resultIntent);
                finish();

            }
        });

    }




}
