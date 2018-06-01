package com.example.angry.googlemap;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class GMap_ViewByPlaceName extends AppCompatActivity {

    SQLiteDatabase db;
    String qry,e1,e2,e3;
    Spinner sp3;
    String  arry[],place;
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
    public Cursor fetch_Names_Data()
    {
        Cursor result=null;
        try {
            qry = "select placeNm from GMapsTable";
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
        setContentView(R.layout.activity_gmap__view_by_place_name);
        sp3=(Spinner)findViewById(R.id.spinner3);
        Connect_Database();
        Cursor rslt = fetch_Names_Data();
        db.close();
        ArrayList list=new ArrayList();
        if(rslt.moveToFirst()) {
            do {
                list.add(rslt.getString(0));
            }while (rslt.moveToNext());
            System.out.println("Exited from arr2");
            rslt.close();
            ArrayAdapter<String> adap1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
            adap1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp3.setAdapter(adap1);
            sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    place=sp3.getSelectedItem().toString();
                    Intent intt=new Intent(GMap_ViewByPlaceName.this,GMap_Map.class);
                    intt.putExtra("Place",place);
                    intt.putExtra("Data","P");
                    startActivity(intt);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
}
