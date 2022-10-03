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

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Global;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
public class FindCovidNearestActivity extends AppCompatActivity{
    ListView listview1;
    ArrayList<String> al=null,destal=null;
    ArrayList<Double> pre_distance=null;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    Button users_loc;
    Runnable run=null;
    Handler handler=null;

    String lat="0.0",lon="0.0",endlat="0.0",endlon="0.0";
    String location="" ;
    LocationManager locationManager ;
    String provider;
    Location location1=null;
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_covid_nearest);


        listview1=(ListView)findViewById(R.id.listview1);
        listview1.setOnItemClickListener(new listview1click());
        new ViewLocations().execute();

        //////////
       geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

    }
    class listview1click implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                long arg3) {

            String s[]=destal.get(position).split("#");
            String add="http://maps.google.com/?saddr="+GlobalVariables.lat+","+GlobalVariables.lon+"&daddr="+s[0]+","+s[1];

            Intent ii=new Intent(Intent.ACTION_VIEW,Uri.parse(add));
            startActivity(ii);
        }
    }
    class ViewLocations extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(FindCovidNearestActivity.this);
        String recs="";
        private static final String TAG_SUCCESS = "success";
        int success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog.setMessage("loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("user_id",GlobalVariables.user_id));
                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.url+"getlocations.php", "GET", params);
               Log.d("Create Response", json.toString());

                success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    recs=json.getString("recs");
                }

            } catch (JSONException e) {
                //e.printStackTrace();
                //Toast.makeText(FindCovidNearestActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            Intent i=null;
           // Toast.makeText(FindCovidNearestActivity.this, "okkkkk" , Toast.LENGTH_LONG).show();

            if (success == 1)
            {
                al=new ArrayList<String>();
                destal=new ArrayList<String>();

                String s[]=recs.split("@@");
                for(String row : s)
                {
                    if(row.length()>1)
                    {
                        String ss[]=row.split("#");
                        double dist=getDistance(ss[1], ss[2]);
                        String address="Location: ";

                        try {
                            List<Address> listAddresses = geocoder.getFromLocation(Double.parseDouble(ss[1]), Double.parseDouble(ss[2]), 1);
                            if(null!=listAddresses&&listAddresses.size()>0){
                                location = listAddresses.get(0).getAddressLine(0);
                                address+=location.toString();
                               // Toast.makeText(FindCovidNearestActivity.this, "Location="+location , Toast.LENGTH_LONG).show();
                            }
                        } catch (IOException e) {
                           // Toast.makeText(FindCovidNearestActivity.this, "Location can't retrive" , Toast.LENGTH_LONG).show();
                        }


                        al.add("\n NAME : "+ss[0]+"\nAddress :"+address+"\nDISTANCE :"+df2.format(dist)+" mts\nLATITUDE: "+ss[1]+"\nLONGITUDE: "+ss[2]);
                        destal.add(ss[1]+"#"+ss[2]);

                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(FindCovidNearestActivity.this, android.R.layout.simple_list_item_1, al);
                listview1.setAdapter(arrayAdapter);
            }
            else
            {

                Toast.makeText(FindCovidNearestActivity.this, "records  not found.  Try again!!!!",Toast.LENGTH_LONG).show();
            }
        }
    }
    public double getDistance(String lat,String lon)
    {

        Location startPoint=new Location("locationA");
        startPoint.setLatitude(Double.parseDouble(lat));
        startPoint.setLongitude(Double.parseDouble(lon));

        Location endPoint=new Location("locationA");
        endPoint.setLatitude(Double.parseDouble(GlobalVariables.lat));
        endPoint.setLongitude(Double.parseDouble(GlobalVariables.lon));

        double distance=startPoint.distanceTo(endPoint);
        return distance;
    }
}