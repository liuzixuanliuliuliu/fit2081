package com.fit2081.fit2081assigment1final;

import android.content.Intent;
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
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Geocoder geocoder;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().setTitle(R.string.nav_maps);
        geocoder = new Geocoder(this, Locale.getDefault());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        LatLng australia = new LatLng(-25.2744, 133.7751);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(australia, 10));

        map.setOnMapClickListener(point -> {
            try {
                List<Address> addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
                if (addresses == null || addresses.isEmpty()) {
                    Toast.makeText(MapsActivity.this, "No Country at this location!", Toast.LENGTH_SHORT).show();
                } else {
                    Address address = addresses.get(0);
                    String countryName = address.getCountryName();
                    if (countryName != null) {
                        Snackbar.make(findViewById(R.id.map), "Country: " + countryName, Snackbar.LENGTH_LONG)
                                .setAction("Details", v -> showCountryDetails(countryName))
                                .show();
                    } else {
                        Toast.makeText(MapsActivity.this, "No Country found!", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (IOException e) {
                Toast.makeText(MapsActivity.this, "Geocoder failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCountryDetails(String countryName) {
        Intent intent = new Intent(this, CountryDetails.class);
        intent.putExtra("country", countryName);
        startActivity(intent);
    }
}
