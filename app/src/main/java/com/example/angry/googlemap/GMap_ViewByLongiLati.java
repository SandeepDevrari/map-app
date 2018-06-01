package com.example.angry.googlemap;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class GMap_ViewByLongiLati extends AppCompatActivity {
    double x,y;
    Spinner sp1,sp2;
    SQLiteDatabase db;
    String qry,e1,e2,e3;
    public void Connect_Database()
    {
        try {
            db = openOrCreateDatabase("GMapsDB", Context.MODE_PRIVATE, null);
            System.out.println("$$$$$$" + db + "$$$$$$$$$");
            qry = "create table if not exists GMapsTable(placeNm varchar,longi int,lati int)";
            db.execSQL(qry);
            System.out.println("Table Created");
        }
        catch(Exception e)
        {
            System.err.println(e.getCause());
            e.printStackTrace();
        }
    }
    public Cursor fetch_Longitude_Data()
    {
        Cursor result=null;
        try {
            qry = "select longi from GMapsTable";
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
    public Cursor fetch_Latitude_Data()
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
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap__view_by_longi_lati);
        sp1=(Spinner)findViewById(R.id.spinner1);
        sp2=(Spinner)findViewById(R.id.spinner2);
        Connect_Database();
        Cursor rslt=fetch_Longitude_Data();
        System.out.println(rslt.getCount());
        ArrayList list1=new ArrayList();
        if(rslt.moveToFirst()) {
            do {
                 list1.add(rslt.getString(0));
            }while (rslt.moveToNext());
            System.out.println("Exited from list1");
            rslt.close();
            ArrayAdapter<String> adap1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list1);
            adap1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp1.setAdapter(adap1);
            sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    x=Double.parseDouble(sp1.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        Cursor rslt1=fetch_Latitude_Data();
        db.close();
        ArrayList list2=new ArrayList();
        if(rslt1.moveToFirst()) {
            do {
                list2.add(rslt1.getString(0));
            }while (rslt1.moveToNext());
            System.out.println("Exited from arr2");
            rslt1.close();
            ArrayAdapter<String> adap2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list2);
            adap2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp2.setAdapter(adap2);
            sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    y=Double.parseDouble(sp2.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
    public void GMap_PlotLongiLati(View v)
    {
        Intent intt=new Intent(GMap_ViewByLongiLati.this,GMap_Map.class);
        intt.putExtra("Longitude",x);
        intt.putExtra("Latitude",y);
        intt.putExtra("Data","L");
        startActivity(intt);
    }
}
