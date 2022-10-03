package com.example.covidforecasting;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class LocationHistoryActivity extends AppCompatActivity {
    ListView listview1;
    ArrayList<String> destlst;

    ArrayList<DataModel> dataModels;
    private static CustomAdapter adapter;

    String lat="0.0",lon="0.0",endlat="0.0",endlon="0.0";
    String location="" ;
    LocationManager locationManager ;
    String provider;
    Location location1=null;
    Geocoder geocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_history);

        listview1=(ListView)findViewById(R.id.listview1);

        //////////
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());



        populatelist();

    }
    public void populatelist()
    {
        new ViewRecords().execute();
    }

    class ViewRecords extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(LocationHistoryActivity.this);
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
                //Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("user_id",GlobalVariables.user_id));
                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.url+"LocationHistoryActivity.php", "GET", params);


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
                //Toast.makeText(LocationHistoryActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }
            return null;
        }


        protected void onPostExecute(String file_url)
        {
            //dismiss the dialog once done
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
                            found=true;
                            String s[]=row.split("~");
                            //String str="latitute : "+s[0]+"\n"+"longitude : "+s[1];


                            String str="";

                            //get address from lat and lon
                            try {
                                List<Address> listAddresses = geocoder.getFromLocation(Double.parseDouble(s[0]), Double.parseDouble(s[1]), 1);
                                if(null!=listAddresses&&listAddresses.size()>0){
                                    location = listAddresses.get(0).getAddressLine(0);
                                    str+=""+location.toString();
                                   // Toast.makeText(LocationHistoryActivity.this, "Location="+location , Toast.LENGTH_LONG).show();
                                }
                            } catch (IOException e) {
                               //Toast.makeText(LocationHistoryActivity.this, "Location can't retrive" , Toast.LENGTH_LONG).show();
                            }

                            dataModels.add(new DataModel("Latitude", s[0], "Longitude",s[1], str));



                            al.add(str);

                        }
                    }
                    //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(LocationHistoryActivity.this,android.R.layout.simple_list_item_1, al);
                    adapter= new CustomAdapter(dataModels,getApplicationContext());
                    listview1.setAdapter(adapter);

                    if(found==false)
                        Toast.makeText(LocationHistoryActivity.this,"Records Not Found",Toast.LENGTH_LONG).show();

                }
                catch(Exception eee)
                {
                    Toast.makeText(LocationHistoryActivity.this,"Error : "+eee,Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(LocationHistoryActivity.this,"Records Not Found",Toast.LENGTH_LONG).show();
            }
        }
    }

}