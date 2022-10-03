package com.example.covidforecasting;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

public class PredictCovidActivity extends AppCompatActivity {
    Button registerbtn;
TextView tvpredict;
    EditText age;

    EditText temperature;

    EditText blood_pressure;

    EditText oxygen_levels;

    EditText critinine_levels;

    Spinner gender;
    Spinner cough;
    Spinner headache;
    Spinner contacted_covid_persion;
    String age_1="";
    String temperature_1="";
    String blood_pressure_1="";
    String oxygen_levels_1="";
    String critinine_levels_1="";
    String gender_1="";
    String cough_1="";
    String headache_1="";
    String contacted_covid_persion_1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_covid);
        tvpredict=(TextView) findViewById(R.id.tvpredict);
        age=(EditText)findViewById(R.id.age);
        temperature=(EditText)findViewById(R.id.temperature);
        blood_pressure=(EditText)findViewById(R.id.blood_pressure);
        oxygen_levels=(EditText)findViewById(R.id.oxygen_levels);
        critinine_levels=(EditText)findViewById(R.id.critinine_levels);
        gender=(Spinner)findViewById(R.id.gender);
        cough=(Spinner)findViewById(R.id.cough);
        headache=(Spinner)findViewById(R.id.headache);
        contacted_covid_persion=(Spinner)findViewById(R.id.contacted_covid_persion);
        registerbtn=(Button)findViewById(R.id.submit);
        registerbtn.setOnClickListener(new submitclick());

        ArrayList<String> al=new ArrayList<String>();
        al.add("Male");
        al.add("Female");
        al.add("Others");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, al);
        gender.setAdapter(adapter);
        ArrayList<String> al1=new ArrayList<String>();
        al1.add("Low");
        al1.add("Moderate");
        al1.add("High");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, al1);
        cough.setAdapter(adapter1);
        ArrayList<String> al2=new ArrayList<String>();
        al2.add("Low");
        al2.add("Moderate");
        al2.add("High");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, al2);
        headache.setAdapter(adapter2);
        ArrayList<String> al3=new ArrayList<String>();
        al3.add("Yes");
        al3.add("No");
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, al3);
        contacted_covid_persion.setAdapter(adapter3);
    }
    class submitclick implements View.OnClickListener
    {
       @Override
        public void onClick(View v)
        {
            age_1=age.getText().toString();
            if(age_1.length()==0)
            {
                Toast.makeText(PredictCovidActivity.this,"Fill age",Toast.LENGTH_LONG).show();
                return;
            }
            temperature_1=temperature.getText().toString();
            if(temperature_1.length()==0)
            {
                Toast.makeText(PredictCovidActivity.this,"Fill temperature",Toast.LENGTH_LONG).show();
                return;
            }
            blood_pressure_1=blood_pressure.getText().toString();
            if(blood_pressure_1.length()==0)
            {
                Toast.makeText(PredictCovidActivity.this,"Fill blood_pressure",Toast.LENGTH_LONG).show();
                return;
            }
            oxygen_levels_1=oxygen_levels.getText().toString();
            if(oxygen_levels_1.length()==0)
            {
                Toast.makeText(PredictCovidActivity.this,"Fill oxygen_levels",Toast.LENGTH_LONG).show();
                return;
            }
            critinine_levels_1=critinine_levels.getText().toString();
            if(critinine_levels_1.length()==0)
            {
                Toast.makeText(PredictCovidActivity.this,"Fill critinine_levels",Toast.LENGTH_LONG).show();
                return;
            }
            gender_1=gender.getSelectedItem().toString();
            cough_1=cough.getSelectedItem().toString();
            headache_1=headache.getSelectedItem().toString();
            contacted_covid_persion_1=contacted_covid_persion.getSelectedItem().toString();
            age.setText("");
            temperature.setText("");
            blood_pressure.setText("");
            oxygen_levels.setText("");
            critinine_levels.setText("");
            gender.setSelection(0);
            cough.setSelection(0);
            headache.setSelection(0);
            contacted_covid_persion.setSelection(0);
            new Predict().execute();

        }
    }
    class Predict extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(PredictCovidActivity.this);
        private static final String TAG_SUCCESS = "success";
        int success=0;
        String result="";

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
                params.add(new BasicNameValuePair("age",age_1));
                params.add(new BasicNameValuePair("temperature",temperature_1));
                params.add(new BasicNameValuePair("blood_pressure",blood_pressure_1));
                params.add(new BasicNameValuePair("oxygen_levels",oxygen_levels_1));
                params.add(new BasicNameValuePair("critinine_levels",critinine_levels_1));
                params.add(new BasicNameValuePair("gender",gender_1));
                params.add(new BasicNameValuePair("cough",cough_1));
                params.add(new BasicNameValuePair("headache",headache_1));
                params.add(new BasicNameValuePair("contacted_covid_persion",contacted_covid_persion_1));
                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.url+"PredictCovidActivity.php", "GET", params);


                Log.d("Create Response", json.toString());



                success = json.getInt(TAG_SUCCESS);
                result=json.getString("result");
            }
            catch (JSONException e)
            {
                //e.printStackTrace();
                //Toast.makeText(PredictCovidActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            //dismiss the dialog once done
            pDialog.dismiss();

            tvpredict.setText("Result: "+ result);
            if (success == 1)
            {
                //Toast.makeText(PredictCovidActivity.this,"Result: "+ result,Toast.LENGTH_LONG).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PredictCovidActivity.this);


                alertDialog.setPositiveButton("Ok", null);

                alertDialog.setMessage("Your covid test result is:  "+ result);
                alertDialog.setTitle("Covid Result");
                alertDialog.show();



            }
            else
            {
                Toast.makeText(PredictCovidActivity.this,"Prediction Failed.  Try Again!",Toast.LENGTH_LONG).show();
            }
        }
    }
}