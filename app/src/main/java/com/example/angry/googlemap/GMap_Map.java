package com.example.angry.googlemap;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class GMap_Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String Place,data;
    double lg,lt;
    public String LocSeLatLong(String searchedAddress){

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        try
        {
            address = coder.getFromLocationName(searchedAddress,5);
            if (address == null) {
                System.out.println("############Address not correct #########");
            }
            Address location = address.get(0);

            System.out.println("Address Latitude : "+ location.getLatitude()+ "Address Longitude : "+ location.getLongitude());
            return(location.getLongitude()+"#"+location.getLatitude());

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return ("");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap__map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intt=getIntent();
        data=intt.getStringExtra("Data");
        if(data.equals("L"))
        {
            Place=null;
            lg=intt.getDoubleExtra("Longitude",0.0);
            lt=intt.getDoubleExtra("Latitude",0.0);
        }
        else
        {
            Place=intt.getStringExtra("Place");
            //lg=-34;
            //lt=151;
            String LongLati=LocSeLatLong(Place);
            String longlati[]=LongLati.split("#");
            lg=Double.parseDouble(longlati[0]);
            lt=Double.parseDouble(longlati[1]);
        }
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(lt,lg);//(-34, 151);
        mMap.addMarker(new MarkerOptions().position(location).title("Marker in Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
