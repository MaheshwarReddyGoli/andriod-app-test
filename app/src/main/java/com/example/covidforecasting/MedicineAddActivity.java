package com.example.covidforecasting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MedicineAddActivity extends AppCompatActivity {
    Button registerbtn;

    EditText medicine_name;

    EditText medicine_time;

    String medicine_name_1="";
    String medicine_time_1="";

    ListView listview1;
    ArrayList<String> destlst;

    ArrayList<DataModel> dataModels;
    private static CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_add);
        medicine_name=(EditText)findViewById(R.id.medicine_name);
        medicine_time=(EditText)findViewById(R.id.medicine_time);
        registerbtn=(Button)findViewById(R.id.submit);
        registerbtn.setOnClickListener(new submitclick());

        listview1=(ListView)findViewById(R.id.listview1);

        populatelist();

    }
    public void populatelist()
    {
        new ViewRecords().execute();
    }

    class submitclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {


            if(medicine_name.getText().toString().length()==0)
            {
                Toast.makeText(getApplicationContext(),"Fill The Field medicine_name",Toast.LENGTH_LONG).show();
                return;
            }

            medicine_name_1=medicine_name.getText().toString();
            if(medicine_name_1.length()==0)
            {
                Toast.makeText(MedicineAddActivity.this,"Fill medicine_name",Toast.LENGTH_LONG).show();
                return;
            }
            medicine_time_1=medicine_time.getText().toString();
            if(medicine_time_1.length()==0)
            {
                Toast.makeText(MedicineAddActivity.this,"Fill medicine_time",Toast.LENGTH_LONG).show();
                return;
            }
            medicine_name.setText("");
            medicine_time.setText("");
            new AddMedicine().execute();

        }
    }
    class AddMedicine extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(MedicineAddActivity.this);
        private static final String TAG_SUCCESS = "success";
        int success=0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog.setMessage("wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("user_id",GlobalVariables.user_id));
                params.add(new BasicNameValuePair("medicine_name",medicine_name_1));
                params.add(new BasicNameValuePair("medicine_time",medicine_time_1));
                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.url+"MedicineAddActivity.php", "GET", params);


                Log.d("Create Response", json.toString());



                success = json.getInt(TAG_SUCCESS);
            }
            catch (JSONException e)
            {
                //e.printStackTrace();
                //Toast.makeText(MedicineAddActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }
            return null;
        }


        protected void onPostExecute(String file_url) {

            pDialog.dismiss();


            if (success == 1)
            {
                new ViewRecords().execute();
                Toast.makeText(MedicineAddActivity.this,"Medicine Added Successfully.",Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(MedicineAddActivity.this,"Record Not Added.  Try Again!",Toast.LENGTH_LONG).show();
            }
        }
    }

    //////////
    class ViewRecords extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(MedicineAddActivity.this);
        private static final String TAG_SUCCESS = "success";
        int success=0;
        String recs="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog.setMessage("wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("user_id",GlobalVariables.user_id));
                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.url+"ViewMedicinesActivity.php", "GET", params);


                Log.d("Create Response", json.toString());

                success = json.getInt(TAG_SUCCESS);
                if(success==1)
                {
                    recs=json.getString("recs");
                }
            }
            catch (JSONException e)
            {
                //e.printStackTrace();
                //Toast.makeText(MedicineAddActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }
            return null;
        }

        protected void onPostExecute(String file_url)
        {

            pDialog.dismiss();


            if (success == 1)
            {
                ArrayList<String> al=new ArrayList<String>();
                dataModels= new ArrayList<>();

                boolean found=false;
                try
                {
                    String rows[]=recs.split("@@");
                    for(String row: rows)
                    {
                        if(row.length()>1)
                        {
                            String s[]=row.split("~");
                            String str="Medicine: "+s[0]+"\n"+"Time: "+s[1];
                            al.add(str);

                            dataModels.add(new DataModel("Medicine", s[0], "Time",s[1], ""));

                            found=true;

                        }
                    }
                  //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MedicineAddActivity.this,android.R.layout.simple_list_item_1, al);
                   // listview1.setAdapter(arrayAdapter);
                    adapter= new CustomAdapter(dataModels,getApplicationContext());
                    listview1.setAdapter(adapter);

                    if(found==false)
                        Toast.makeText(MedicineAddActivity.this,"Records Not Found",Toast.LENGTH_LONG).show();

                }
                catch(Exception eee)
                {
                    Toast.makeText(MedicineAddActivity.this,"Error : "+eee,Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(MedicineAddActivity.this,"Records Not Found",Toast.LENGTH_LONG).show();
            }
        }
    }
}