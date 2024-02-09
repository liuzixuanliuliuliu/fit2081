package com.fit2081.fit2081assigment1final;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Geocoder geocoder;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps); // Make sure you have a layout named activity_maps.xml
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.nav_maps);
        }
        geocoder = new Geocoder(this, Locale.getDefault());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map); // Ensure you have a fragment with id map in activity_maps.xml
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng australia = new LatLng(-25.2744, 133.7751); // Coordinates for Australia
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(australia, 10));
        googleMap.setOnMapClickListener(this::onMapClick);
    }

    private void onMapClick(LatLng point) {
        try {
            List<Address> addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
            if (addresses.isEmpty()) {
                showToast("No Country at this location!");
            } else {
                Address address = addresses.get(0);
                String countryName = address.getCountryName();
                if (countryName != null) {
                    showToast("Country: " + countryName);
                } else {
                    showToast("No Country at this location!");
                }
            }
        } catch (IOException e) {
            showToast("Error finding country: " + e.getMessage());
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
