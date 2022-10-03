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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class LoginActivity extends AppCompatActivity {
    Button registerbtn;

    TextView newregtv, forgotpass;

    EditText email;

    EditText password;

    String email_1="";
    String password_1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        newregtv=(TextView)findViewById(R.id.newregtv);
        newregtv.setOnClickListener(new newregtvclick());


        registerbtn=(Button)findViewById(R.id.submit);
        registerbtn.setOnClickListener(new submitclick());

        forgotpass=(TextView)findViewById(R.id.forgotpass);
        forgotpass.setOnClickListener(new ForgotPassclick());

    }
    class newregtvclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {

            Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(i);

        }

    }
    class ForgotPassclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {

            Intent i=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
            startActivity(i);

        }

    }
    class submitclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {


            if(email.getText().toString().length()==0)
            {
                Toast.makeText(getApplicationContext(),"Fill The Field email",Toast.LENGTH_LONG).show();
                return;
            }

            if(password.getText().toString().length()==0)
            {
                Toast.makeText(getApplicationContext(),"Fill The Field password",Toast.LENGTH_LONG).show();
                return;
            }

            email_1=email.getText().toString();
            if(email_1.length()==0)
            {
                Toast.makeText(LoginActivity.this,"Fill email",Toast.LENGTH_LONG).show();
                return;
            }
            password_1=password.getText().toString();
            if(password_1.length()==0)
            {
                Toast.makeText(LoginActivity.this,"Fill password",Toast.LENGTH_LONG).show();
                return;
            }
            new Login().execute();
        }
    }
    class Login extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(LoginActivity.this);
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
                params.add(new BasicNameValuePair("email",email_1));
                params.add(new BasicNameValuePair("password",password_1));
                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.url+"LoginActivity.php", "GET", params);


                Log.d("Create Response", json.toString());

                success = json.getInt(TAG_SUCCESS);
                GlobalVariables.user_id=json.getString("user_id");
                recs=json.getString("recs");
            }
            catch (JSONException e)
            {
                //e.printStackTrace();
                //Toast.makeText(LoginActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }
            return null;
        }


        protected void onPostExecute(String file_url) {

            pDialog.dismiss();


            if (success == 1)
            {
                String s[]=recs.split("@");
                for(String ss: s)
                {
                    if(ss.length() > 5) {
                        String x[] = ss.split("#");
                       GlobalVariables.hm.put(x[0], "Dosage: " + x[1] + "\nNext Date: " + x[2]);
                    }
                }


                //Toast.makeText(LoginActivity.this,"Login Success." +GlobalVariables.user_id,Toast.LENGTH_LONG).show();
                Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(LoginActivity.this,"Login Failed.  Try Again!",Toast.LENGTH_LONG).show();
            }
        }
    }
}