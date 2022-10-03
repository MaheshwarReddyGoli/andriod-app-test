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
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    Button registerbtn;

    EditText first_name;

    EditText last_name;

    EditText height;

    EditText weight;

    Spinner gender;
    EditText dob;

    EditText email;

    EditText phoneno;

    String first_name_1="";
    String last_name_1="";
    String height_1="";
    String weight_1="";
    String gender_1="";
    String dob_1="";
    EditText password,confirmpassword;

    String email_1="";
    String password_1="";
    String phoneno_1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        first_name=(EditText)findViewById(R.id.first_name);
        last_name=(EditText)findViewById(R.id.last_name);
        height=(EditText)findViewById(R.id.height);
        weight=(EditText)findViewById(R.id.weight);
        gender=(Spinner)findViewById(R.id.gender);
        dob=(EditText)findViewById(R.id.dob);
        password=(EditText)findViewById(R.id.password);
        confirmpassword=(EditText)findViewById(R.id.confirmpassword);
        dob.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        dob.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    }};
                Calendar myCalendar=Calendar.getInstance();
                int year=myCalendar.get(Calendar.YEAR);
                int month=myCalendar.get(Calendar.MONTH+1);
                int day=myCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpDialog=new DatePickerDialog(RegisterActivity.this, listener, year, month, day);
                dpDialog.show();
            }
        });
        email=(EditText)findViewById(R.id.email);
        phoneno=(EditText)findViewById(R.id.phoneno);
        registerbtn=(Button)findViewById(R.id.submit);
        registerbtn.setOnClickListener(new submitclick());

        ArrayList<String> al=new ArrayList<String>();
        al.add("Male");
        al.add("Female");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, al);
        gender.setAdapter(adapter);
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
                Toast.makeText(RegisterActivity.this,"Fill first_name",Toast.LENGTH_LONG).show();
                return;
            }
            last_name_1=last_name.getText().toString();
            if(last_name_1.length()==0)
            {
                Toast.makeText(RegisterActivity.this,"Fill last_name",Toast.LENGTH_LONG).show();
                return;
            }
            height_1=height.getText().toString();
            if(height_1.length()==0)
            {
                Toast.makeText(RegisterActivity.this,"Fill height",Toast.LENGTH_LONG).show();
                return;
            }
            weight_1=weight.getText().toString();
            if(weight_1.length()==0)
            {
                Toast.makeText(RegisterActivity.this,"Fill weight",Toast.LENGTH_LONG).show();
                return;
            }
            gender_1=gender.getSelectedItem().toString();
            dob_1=dob.getText().toString();
            if(dob_1.length()==0)
            {
                Toast.makeText(RegisterActivity.this,"Fill dob",Toast.LENGTH_LONG).show();
                return;
            }
            email_1=email.getText().toString();
            if(email_1.length()==0)
            {
                Toast.makeText(RegisterActivity.this,"Fill email",Toast.LENGTH_LONG).show();
                return;
            }
            phoneno_1=phoneno.getText().toString();
            if(phoneno_1.length()==0)
            {
                Toast.makeText(RegisterActivity.this,"Fill phoneno",Toast.LENGTH_LONG).show();
                return;
            }
            password_1=password.getText().toString();
            if(password_1.length()==0)
            {
                Toast.makeText(RegisterActivity.this,"Fill password",Toast.LENGTH_LONG).show();
                return;
            }

            if(!password_1.equals(confirmpassword.getText().toString()))
            {
                Toast.makeText(RegisterActivity.this,"passwords not match",Toast.LENGTH_LONG).show();
                return;
            }
            first_name.setText("");
            last_name.setText("");
            height.setText("");
            weight.setText("");
            gender.setSelection(0);
            dob.setText("");
            email.setText("");
            phoneno.setText("");

            new NewRegistration().execute();

        }
    }
    class NewRegistration extends AsyncTask<String, String, String>
    {

        private final ProgressDialog pDialog = new ProgressDialog(RegisterActivity.this);
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
                params.add(new BasicNameValuePair("first_name",first_name_1));
                params.add(new BasicNameValuePair("last_name",last_name_1));
                params.add(new BasicNameValuePair("height",height_1));
                params.add(new BasicNameValuePair("weight",weight_1));
                params.add(new BasicNameValuePair("gender",gender_1));
                params.add(new BasicNameValuePair("dob",dob_1));
                params.add(new BasicNameValuePair("email",email_1));
                params.add(new BasicNameValuePair("phoneno",phoneno_1));
                params.add(new BasicNameValuePair("password",password_1));
                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.url+"RegisterActivity.php", "GET", params);


                Log.d("Create Response", json.toString());



                success = json.getInt(TAG_SUCCESS);
            }
            catch (JSONException e)
            {
                //e.printStackTrace();
                //Toast.makeText(RegisterActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }
            return null;
        }


        protected void onPostExecute(String file_url) {

            pDialog.dismiss();


            if (success == 1)
            {
                Toast.makeText(RegisterActivity.this,"Registration Success.",Toast.LENGTH_LONG).show();
                Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(RegisterActivity.this,"Registration Failed.  Try Again!",Toast.LENGTH_LONG).show();
            }
        }
    }
}