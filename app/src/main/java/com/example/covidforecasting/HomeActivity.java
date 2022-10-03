package com.example.covidforecasting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements LocationListener {
    Button CovidPrediction;
    Button bmi;
    Button vaccinated;
    Button medicinereminder;
    Button nearcovid, nearestpharmacy;
    Button FAQs;
    boolean flag;
Button userpic,logoutpic,lochistory,medicine;

    String lat = "0.0", lon = "0.0";
    LocationManager locationManager;
    String provider;

    Location location = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CovidPrediction = (Button) findViewById(R.id.CovidPrediction);
        CovidPrediction.setOnClickListener(new CovidPredictionclick());
        bmi = (Button) findViewById(R.id.bmi);
        bmi.setOnClickListener(new bmiclick());
        vaccinated = (Button) findViewById(R.id.vaccinated);
        vaccinated.setOnClickListener(new vaccinatedclick());
        medicinereminder = (Button) findViewById(R.id.medicinereminder);
        medicinereminder.setOnClickListener(new medicinereminderclick());
        nearcovid = (Button) findViewById(R.id.nearcovid);
        nearcovid.setOnClickListener(new nearcovidclick());
        FAQs = (Button) findViewById(R.id.FAQs);
        FAQs.setOnClickListener(new FAQsclick()
        );


        lochistory = (Button) findViewById(R.id.lochistory);
        lochistory.setOnClickListener(new lochistoryclick());


        userpic = (Button) findViewById(R.id.userpic);
        userpic.setOnClickListener(new userpicclick());



        nearestpharmacy = (Button) findViewById(R.id.nearestpharmacy);
        nearestpharmacy.setOnClickListener(new nearestpharmacyclick());

        logoutpic = (Button) findViewById(R.id.logoutpic);
        logoutpic.setOnClickListener(new logoutpicclick());
flag=false;


        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        // Getting LocationManager object
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Creating an empty criteria object
        Criteria criteria = new Criteria();

        // Getting the name of the provider that meets the criteria
        provider = locationManager.getBestProvider(criteria, false);
        try {
            if (provider != null && !provider.equals("")) {
                location = locationManager.getLastKnownLocation(provider);
                locationManager.requestLocationUpdates(provider, 3000, 1, HomeActivity.this);

                if(location!=null)
                    onLocationChanged(location);
                else
                    Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
            }

        }catch(Exception eee)
        {

        }
        new ViewRecords().execute();

    }

    @Override
    public void onBackPressed() {

        //  super.onBackPressed();
        doExit();
    }

    private void doExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                HomeActivity.this);

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(DialogInterface dialog, int which) {

                HomeActivity.this.finishAffinity();
                System.exit(0);
            }
        });

        alertDialog.setNegativeButton("No", null);

        alertDialog.setMessage("Do you want to exit?");
        alertDialog.setTitle("Alert");
        alertDialog.show();
    }



    @Override
    public void onLocationChanged(Location location) {
        Log.d("Latitude", "changing location");

        lat=""+location.getLatitude();
        lon=""+location.getLongitude();

        GlobalVariables.lat=lat;
        GlobalVariables.lon=lon;

        if(flag==false)
            new AddLocation().execute();
        flag=true;

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    class CovidPredictionclick implements View.OnClickListener
    {
       @Override
        public void onClick(View v)
        {
            Intent i=new Intent(HomeActivity.this,PredictCovidActivity.class);
            startActivity(i);
        }
    }

    class bmiclick implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
           Intent i=new Intent(HomeActivity.this,BMIActivity.class);
            startActivity(i);
        }
    }

    class vaccinatedclick implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {

            Intent i=new Intent(HomeActivity.this,VaccinationActivity.class);
            startActivity(i);
        }
    }

    class medicinereminderclick implements View.OnClickListener
    {
       @Override
        public void onClick(View v)
        {

            Intent i=new Intent(HomeActivity.this,MedicineAddActivity.class);
            startActivity(i);
        }
    }

    class pharmacylistclick implements View.OnClickListener
    {
       @Override
        public void onClick(View v)
        {

        }
    }

    class FAQsclick implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Intent i=new Intent(HomeActivity.this,FAQActivity.class);
            startActivity(i);
        }
    }
    class userpicclick implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Intent i=new Intent(HomeActivity.this,ProfileActivity.class);
            startActivity(i);
        }
    }
    class lochistoryclick implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Intent i=new Intent(HomeActivity.this,LocationHistoryActivity.class);
            startActivity(i);
        }
    }

    class nearcovidclick implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Intent i=new Intent(HomeActivity.this,FindCovidNearestActivity.class);
            startActivity(i);
        }
    }

    class nearestpharmacyclick implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            String url = "http://maps.google.co.uk/maps?q=Pharmacy&hl=en";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
            startActivity(intent);
        }
    }
    class logoutpicclick implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            HomeActivity.this.finishAffinity();
            System.exit(0);
        }
    }

    class AddLocation extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(HomeActivity.this);
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
                //Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("user_id",GlobalVariables.user_id));
                params.add(new BasicNameValuePair("latitude",lat));
                params.add(new BasicNameValuePair("longitude",lon));
                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.url+"AddLocation.php", "GET", params);

                //check log cat fro response
                Log.d("Create Response", json.toString());

                //check for success tag

                success = json.getInt(TAG_SUCCESS);

            }
            catch (JSONException e)
            {
                //e.printStackTrace();
                //Toast.makeText(HomeActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }
            return null;
        }


        protected void onPostExecute(String file_url) {

            pDialog.dismiss();


            if (success == 1)
            {
                Toast.makeText(HomeActivity.this,"Location Added." ,Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(HomeActivity.this,"Location not added. ",Toast.LENGTH_LONG).show();
            }
        }
    }

    /////////
    class ViewRecords extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(HomeActivity.this);
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

                boolean found=false;
                String str="";
                try
                {
                    String rows[]=recs.split("@@");
                    for(String row: rows)
                    {
                        if(row.length()>1)
                        {
                            String s[]=row.split("~");
                            str+="Medicine: "+s[0]+"\n"+"Time: "+s[1];
                            str+="\n---------------------------------------\n";



                            found=true;

                        }
                    }

                    if(str.length()>1)
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                                HomeActivity.this);




                        alertDialog.setPositiveButton("Ok", null);

                        alertDialog.setMessage(str);
                        alertDialog.setTitle("Medicine Alert");
                        alertDialog.show();
                    }


                    if(found==false)
                        Toast.makeText(HomeActivity.this,"Records Not Found",Toast.LENGTH_LONG).show();

                }
                catch(Exception eee)
                {
                    Toast.makeText(HomeActivity.this,"Error : "+eee,Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                //Toast.makeText(HomeActivity.this,"Records Not Found",Toast.LENGTH_LONG).show();
            }
        }
    }

    /////////


}