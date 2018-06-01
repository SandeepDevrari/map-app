package com.example.angry.googlemap;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class FavOnMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_on_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intt=getIntent();
        list=intt.getStringArrayListExtra("fav");

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.setBuildingsEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng india = new LatLng(22, 77);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            final Spinner style=(Spinner)findViewById(R.id.stylespinner);
            style.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    i=style.getSelectedItemPosition();
                    if(i==1)
                    {
                        dark(googleMap);
                    }else if(i==2)
                    {
                        Aubergine(googleMap);
                    }else if(i==3)
                    {
                        silver(googleMap);
                    }else if(i==4)
                    {
                        custom(googleMap);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } catch (Resources.NotFoundException e) {
            e.getStackTrace();
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(india));
        String temp;
        String[] tem;
        for(int t=0;t<list.size();t++){
            temp=list.get(t);
            tem=temp.split("#");
            LatLng tempMark = new LatLng(Double.parseDouble(tem[0]), Double.parseDouble(tem[1]));
            mMap.addMarker(new MarkerOptions().position(tempMark).title(tem[0]+","+tem[1]));
        }
    }
    private void custom(GoogleMap googleMap)
    {
        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.mapstyle));
    }
    private void silver(GoogleMap googleMap)
    {
        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.silver));
    }
    private void dark(GoogleMap googleMap)
    {
        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.dark));
    }
    private void Aubergine(GoogleMap googleMap)
    {
        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.aubergine));
    }
}
