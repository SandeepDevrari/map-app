package com.example.angry.googlemap;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GMap_Home extends AppCompatActivity {

    RadioGroup rgb1;
    RadioButton rb0,rb1,rb2;//,rb3;
    //Button loc;
    MyLocation myloc;
    SQLiteDatabase db;
    String qry,e1,e2,e3;
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
    public Cursor fetch_Data()
    {
        Cursor result=null;
        try {
            qry = "select lati,longi from GMapsTable";
            result=db.rawQuery(qry,null);
            if(result.moveToFirst())
                System.out.println("Data Fetched");
        }
        catch(Exception e)
        {
            System.err.println(e.getCause());
            e.printStackTrace();
        }
        return(result);
    }
    /*public Cursor fetch_Latitude_Data()
    {
        Cursor result=null;
        try {
            qry = "select lati from GMapsTable";
            result=db.rawQuery(qry,null);
            if(result.moveToFirst())
                System.out.println("Data Fetched");
        }
        catch(Exception e)
        {
            System.err.println(e.getCause());
            e.printStackTrace();
        }
        return(result);
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap__home);
        //loc=(Button)findViewById(R.id.myLocation);
        rgb1=(RadioGroup)findViewById(R.id.radioGroup1);
        rb0=(RadioButton)findViewById(R.id.radioButton0);
        rb1=(RadioButton)findViewById(R.id.radioButton1);
        rb2=(RadioButton)findViewById(R.id.radioButton2);
        //rb3=(RadioButton)findViewById(R.id.radioButton3);
        rgb1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                i = rgb1.getCheckedRadioButtonId();
                if (rb1.getId() == i) {
                    Intent intt = new Intent(GMap_Home.this, GMap_AddLocation.class);
                    startActivity(intt);
                } else if (rb2.getId() == i) {
                    Connect_Database();
                    //HashMap<String,String> location=new HashMap<String, String>();
                    Cursor rslt = fetch_Data();
                    System.out.println(rslt.getCount());
                    ArrayList list1 = new ArrayList();
                    if (rslt.moveToFirst()) {
                        do {
                            //location.put(rslt.getString(0),rslt.getString(1));
                            list1.add(rslt.getString(0) + "#" + rslt.getString(1));//location.get(rslt.getString(0)));
                        } while (rslt.moveToNext());
                        for (int t = 0; t < list1.size(); t++)
                            System.out.println("%%%%%%%%%%%%%" + list1.get(t));
                        System.out.println("Exited from list1");
                        rslt.close();
                        Intent intt = new Intent(GMap_Home.this, FavOnMap.class);
                        intt.putExtra("fav", list1);
                        startActivity(intt);
                    }
                }
                //} else if (rb3.getId() == i) {
                  //  Intent intt = new Intent(GMap_Home.this, GMap_ViewByPlaceName.class);
                    //startActivity(intt);
                 else if (rb0.getId() == i) {
                    double latitude=0.0,longitude=0.0;
                    myloc=new MyLocation(GMap_Home.this);
                    if (myloc.canGetLocation()) {

                        latitude = myloc.getLatitude();
                        longitude = myloc.getLongitude();

                        System.out.println("*****latitude: " + latitude + "\nlongitude: " + longitude);
                    }
                    Intent intt=new Intent(GMap_Home.this,SearchOnMap.class);
                    //intt.putExtra("search",query);
                    intt.putExtra("lati",""+latitude);
                    intt.putExtra("longi",""+longitude);
                    startActivity(intt);
                }
        }});
        /*loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double latitude=0.0,longitude=0.0;
                myloc=new MyLocation(GMap_Home.this);
                if (myloc.canGetLocation()) {

                    latitude = myloc.getLatitude();
                    longitude = myloc.getLongitude();

                    System.out.println("*****latitude: " + latitude + "\nlongitude: " + longitude);
                }
                Intent intt=new Intent(GMap_Home.this,SearchOnMap.class);
                //intt.putExtra("search",query);
                intt.putExtra("lati",""+latitude);
                intt.putExtra("longi",""+longitude);
                startActivity(intt);
            }
        });*/
        handleIntent(getIntent());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchmenu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;

    }
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
    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if(query==" ")
            {
                Toast.makeText(this,"try to type",Toast.LENGTH_LONG).show();
            }
            else
            {
                System.out.println("$$$$$$$$$$$$$$"+query);
                String LongLati=LocSeLatLong(query);
                String longlati[]=LongLati.split("#");
                Double longi=Double.parseDouble(longlati[0]);
                Double lati=Double.parseDouble(longlati[1]);
                Intent intt=new Intent(GMap_Home.this,SearchOnMap.class);
                //intt.putExtra("search",query);
                intt.putExtra("lati",""+lati);
                intt.putExtra("longi",""+longi);
                startActivity(intt);
            }
        }
    }
}