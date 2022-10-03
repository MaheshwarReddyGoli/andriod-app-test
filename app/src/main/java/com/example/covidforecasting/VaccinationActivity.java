package com.example.covidforecasting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class VaccinationActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton moderna,pfizer,johnson;
    TextView result1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);
        moderna = (RadioButton) findViewById(R.id.moderna);
        pfizer = (RadioButton) findViewById(R.id.pfizer);
        johnson = (RadioButton) findViewById(R.id.johnson);
        result1=(TextView)findViewById(R.id.result1);
        moderna.setOnClickListener(new modernaclick());
        pfizer.setOnClickListener(new pfizerclick());
        johnson.setOnClickListener(new johnsonclick());


    }

    class modernaclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            result1.setText(GlobalVariables.hm.get("moderna"));
        }
    }

    class pfizerclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            result1.setText(GlobalVariables.hm.get("pfizer"));
        }
    }

    class johnsonclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            result1.setText(GlobalVariables.hm.get("Johnson and Johnson"));
        }
    }
}