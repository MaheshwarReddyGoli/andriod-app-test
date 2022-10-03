package com.example.covidforecasting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ForgotPasswordActivity extends AppCompatActivity {
EditText mobile;
String mobileno;
    Button getpwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mobile = (EditText)findViewById(R.id.mobile);

        getpwd=(Button)findViewById(R.id.getpwd);
        getpwd.setOnClickListener(new getpwdclick());

    }
    class getpwdclick implements View.OnClickListener
    {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            mobileno=mobile.getText().toString();
            if(mobileno.length()==0 ){
                Toast.makeText(ForgotPasswordActivity.this, "Enter  Mobile Number", Toast.LENGTH_LONG).show();
            }
            else
            {
                new ForgotPass().execute();
            }

        }

    }
    class ForgotPass extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(ForgotPasswordActivity.this);
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
                params.add(new BasicNameValuePair("mobileno",mobileno));
                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.url+"forgotpassword.php", "GET", params);


                Log.d("Create Response", json.toString());

                success = json.getInt(TAG_SUCCESS);

            }
            catch (JSONException e)
            {

            }
            return null;
        }


        protected void onPostExecute(String file_url) {

            pDialog.dismiss();


            if (success == 1)
            {
                Toast.makeText(ForgotPasswordActivity.this,"Password sent to register email-id.",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(ForgotPasswordActivity.this,"Invalid Mobile Number.  Try Again!",Toast.LENGTH_LONG).show();
            }
        }
    }





}