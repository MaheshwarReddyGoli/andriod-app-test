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

public class ProfileActivity extends AppCompatActivity {
    ListView listview1;
    ArrayList<String> destlst;
    Button registerbtn;

    EditText first_name;

    EditText last_name;

    EditText height;

    EditText weight;

    EditText gender;
    EditText dob;

    EditText email;

    EditText phoneno;

    String first_name_1="";
    String last_name_1="";
    String height_1="";
    String weight_1="";
    String gender_1="";
    String dob_1="";



    String email_1="";

    String phoneno_1="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        first_name=(EditText)findViewById(R.id.first_name);
        last_name=(EditText)findViewById(R.id.last_name);
        height=(EditText)findViewById(R.id.height);
        weight=(EditText)findViewById(R.id.weight);
        gender=(EditText)findViewById(R.id.gender);
        dob=(EditText)findViewById(R.id.dob);
        email=(EditText)findViewById(R.id.email);
        phoneno=(EditText)findViewById(R.id.phoneno);
        registerbtn=(Button)findViewById(R.id.submit);
        registerbtn.setOnClickListener(new submitclick());

        populatelist();

    }
    public void populatelist()
    {
        new ViewRecords().execute();
    }

    class ViewRecords extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(ProfileActivity.this);
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
                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.url+"ProfileActivity.php", "GET", params);


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
                //Toast.makeText(ProfileActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }
            return null;
        }

        protected void onPostExecute(String file_url)
        {
            //dismiss the dialog once done
            pDialog.dismiss();


            if (success == 1)
            {

                boolean found=false;
                try
                {
                    String rows[]=recs.split("@@");
                    for(String row: rows)
                    {
                        if(row.length()>1)
                        {
                            String s[]=row.split("~");

                            first_name.setText(s[0]);
                            last_name.setText(s[0]);
                            height.setText(s[2]);
                            weight.setText(s[3]);
                            gender.setText(s[4]);
                            dob.setText(s[5]);
                            email.setText(s[6]);
                            phoneno.setText(s[7]);

                        }
                    }


                }
                catch(Exception eee)
                {
                    Toast.makeText(ProfileActivity.this,"Error : "+eee,Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(ProfileActivity.this,"Profile Not Found",Toast.LENGTH_LONG).show();
            }
        }
    }


    class submitclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {


            if(first_name.getText().toString().length()==0)
            {
                Toast.makeText(getApplicationContext(),"Fill The Field first_name",Toast.LENGTH_LONG).show();
                return;
            }

            if(last_name.getText().toString().length()==0)
            {
                Toast.makeText(getApplicationContext(),"Fill The Field last_name",Toast.LENGTH_LONG).show();
                return;
            }

            if(height.getText().toString().length()==0)
            {
                Toast.makeText(getApplicationContext(),"Fill The Field height",Toast.LENGTH_LONG).show();
                return;
            }

            if(weight.getText().toString().length()==0)
            {
                Toast.makeText(getApplicationContext(),"Fill The Field weight",Toast.LENGTH_LONG).show();
                return;
            }

            if(email.getText().toString().length()==0)
            {
                Toast.makeText(getApplicationContext(),"Fill The Field email",Toast.LENGTH_LONG).show();
                return;
            }

            if(phoneno.getText().toString().length()==0)
            {
                Toast.makeText(getApplicationContext(),"Fill The Field phoneno",Toast.LENGTH_LONG).show();
                return;
            }

            first_name_1=first_name.getText().toString();
            if(first_name_1.length()==0)
            {
                Toast.makeText(ProfileActivity.this,"Fill first_name",Toast.LENGTH_LONG).show();
                return;
            }
            last_name_1=last_name.getText().toString();
            if(last_name_1.length()==0)
            {
                Toast.makeText(ProfileActivity.this,"Fill last_name",Toast.LENGTH_LONG).show();
                return;
            }
            height_1=height.getText().toString();
            if(height_1.length()==0)
            {
                Toast.makeText(ProfileActivity.this,"Fill height",Toast.LENGTH_LONG).show();
                return;
            }
            weight_1=weight.getText().toString();
            if(weight_1.length()==0)
            {
                Toast.makeText(ProfileActivity.this,"Fill weight",Toast.LENGTH_LONG).show();
                return;
            }
            gender_1=gender.getText().toString();
            dob_1=dob.getText().toString();
            if(dob_1.length()==0)
            {
                Toast.makeText(ProfileActivity.this,"Fill dob",Toast.LENGTH_LONG).show();
                return;
            }
            email_1=email.getText().toString();
            if(email_1.length()==0)
            {
                Toast.makeText(ProfileActivity.this,"Fill email",Toast.LENGTH_LONG).show();
                return;
            }
            phoneno_1=phoneno.getText().toString();
            if(phoneno_1.length()==0)
            {
                Toast.makeText(ProfileActivity.this,"Fill phoneno",Toast.LENGTH_LONG).show();
                return;
            }
            new ProfileUpdate().execute();

        }
    }
    class ProfileUpdate extends AsyncTask<String, String, String>
    {

        private final ProgressDialog pDialog = new ProgressDialog(ProfileActivity.this);
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
                params.add(new BasicNameValuePair("first_name",first_name_1));
                params.add(new BasicNameValuePair("last_name",last_name_1));
                params.add(new BasicNameValuePair("height",height_1));
                params.add(new BasicNameValuePair("weight",weight_1));
                params.add(new BasicNameValuePair("gender",gender_1));
                params.add(new BasicNameValuePair("dob",dob_1));
                params.add(new BasicNameValuePair("email",email_1));
                params.add(new BasicNameValuePair("phoneno",phoneno_1));

                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.url+"UpdateProfile.php", "GET", params);
                Log.d("Create Response", json.toString());
                success = json.getInt(TAG_SUCCESS);
            }
            catch (JSONException e)
            {
                //e.printStackTrace();
                //Toast.makeText(ProfileActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }
            return null;
        }


        protected void onPostExecute(String file_url) {

            pDialog.dismiss();


            if (success == 1)
            {
                Toast.makeText(ProfileActivity.this,"Profile updated.",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(ProfileActivity.this,"Profile not updated.  Try Again!",Toast.LENGTH_LONG).show();
            }
        }
    }

}