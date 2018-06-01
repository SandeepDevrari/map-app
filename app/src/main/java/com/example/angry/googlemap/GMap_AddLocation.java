package com.example.angry.googlemap;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GMap_AddLocation extends AppCompatActivity {

    Button bt1;
    EditText et2,et3;//et1,
    SQLiteDatabase db;
    String qry,e2,e3;
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
    public void delete_Data()
    {
        try {
            qry = "delete from GMapsTable";
            db.execSQL(qry);
            System.out.println("Data Deleted");
        }
        catch(Exception e)
        {
            System.err.println(e.getCause());
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap__add_location);
       // et1=(EditText)findViewById(R.id.editText1);
        et2=(EditText)findViewById(R.id.editText2);
        et3=(EditText)findViewById(R.id.editText31);
        bt1=(Button)findViewById(R.id.button1);
        Connect_Database();
        //delete_Data();
        /*et1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                et1.setText("");
                et1.setEnabled(false);
                //et3.setEnabled(false);
            }
        });*/
        et2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(et3.getText().toString()==null)
                {
                    Toast.makeText(getApplicationContext(),"fill longitude",Toast.LENGTH_LONG).show();
                }
            }
        });
        et3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(et2.getText().toString()==null)
                {
                    Toast.makeText(getApplicationContext(),"fill latitude",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void GMap_Save_Data(View v)
    {
        if(et2.getText().toString()!=null || et3.getText().toString()!=null)
        {
            if(et2.getText().toString()==null || et2.getText().toString()==" ")
            {
                Toast.makeText(this,"fill longitude",Toast.LENGTH_LONG).show();
            }
            if(et3.getText().toString()==null || et3.getText().toString()==" ")
            {
                Toast.makeText(this,"fill latitude",Toast.LENGTH_LONG).show();
            }

        }
        /*else if(et1.getText().toString()!=null)
        {
        if((et1.getText().toString()).equals(""))
        {
            e1=" ";
        }
        else
        {
            e1=et1.getText().toString();
        }*/
        if((et2.getText().toString()).equals(""))
        {
            e2="0";
        }
        else
        {
            e2=et2.getText().toString();
            System.out.println("et2 run");
        }
        if((et3.getText().toString()).equals(""))
        {
            e3="0";
        }
        else
        {
            e3=et3.getText().toString();
        }
        insert_Data();
        db.close();
        Intent intt=new Intent(GMap_AddLocation.this,GMap_Home.class);
        startActivity(intt);
    }
}
