package com.example.angry.googlemap;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static android.R.drawable.btn_star_big_on;

public class SearchOnMap extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener,OnMapReadyCallback {

    private GoogleMap mMap;
    //String search;
    double lg,lt;
    ImageButton savetofav;
    SQLiteDatabase db;
    String qry,e2,e3;
    Spinner maptype;
    public void Connect_Database()
    {
        try {
            db = openOrCreateDatabase("GMapsDB", Context.MODE_PRIVATE, null);
            System.out.println("$$$$$$" + db + "$$$$$$$$$");
            qry = "create table if not exists GMapsTable(longi varchar,lati varchar)";//placeNm varchar,
            db.execSQL(qry);
            System.out.println("Table Created");
        }
        catch(Exception e)
        {
            System.err.println(e.getCause());
            e.printStackTrace();
        }
    }
    public void insert_Data()
    {
        try {
            qry = "insert into GMapsTable values('"+e2+"','"+e3+"')";//'"+e1+"',
            System.out.println("####"+qry);
            db.execSQL(qry);
            System.out.println("Data Inserted");
        }
        catch(Exception e)
        {
            System.err.println(e.getCause());
            e.printStackTrace();
        }
    }
    /*public String LocSeLatLong(String searchedAddress){

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
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_on_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intt=getIntent();
        lt=Double.parseDouble(intt.getStringExtra("lati"));
        lg=Double.parseDouble(intt.getStringExtra("longi"));
        e2=String.valueOf(lg);
        e3=String.valueOf(lt);
        savetofav=(ImageButton)findViewById(R.id.storetofav);
        savetofav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connect_Database();
                insert_Data();
               // savetofav.setImageDrawable(@drawable/btn_star_big_on);
            }
        });

        //search=intt.getStringExtra("search");
        //String LongLati=LocSeLatLong(search);
        //String longlati[]=LongLati.split("#");
        //lg=Double.parseDouble(longlati[0]);
        //lt=Double.parseDouble(longlati[1]);
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                // Permission was denied. Display an error message.
            }
        }*/
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

        maptype=(Spinner)findViewById(R.id.maptype);
        ArrayList<String> type=new ArrayList<String>();
        type.add("Satellite");
        type.add("Normal");
        type.add("Terrain");
        type.add("Hybrid");
        type.add("None");
        ArrayAdapter<String>typeadap=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,type);
        typeadap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maptype.setAdapter(typeadap);
        maptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                i=maptype.getSelectedItemPosition();
                if(i==0)
                {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }else if(i==1)
                {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }else if(i==2)
                {
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }else if(i==3)
                {
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }else if(i==4)
                {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lt, lg);
        mMap.addMarker(new MarkerOptions().position(sydney).title(lt+" "+lg));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return true;
    }
}
