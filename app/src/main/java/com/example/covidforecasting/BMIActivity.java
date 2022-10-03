package com.example.covidforecasting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BMIActivity extends AppCompatActivity {
    TextView result;
   EditText height;
   EditText weight;
    Button registerbtn,chart1;

    String height_1="";
    String weight_1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactivity);

        height=(EditText)findViewById(R.id.height);
        weight=(EditText)findViewById(R.id.weight);
        result=(TextView)findViewById(R.id.result);
        chart1=(Button) findViewById(R.id.chart1);


        registerbtn=(Button)findViewById(R.id.submit);
        registerbtn.setOnClickListener(new submitclick());

        chart1.setOnClickListener(new chart1click());
    }
    class submitclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            try {
                float height_1= Float.parseFloat( height.getText().toString() );
                float weight_1= Float.parseFloat( weight.getText().toString() );

                float bmi=(weight_1 /  (height_1*height_1)) * 703;
                result.setText("Result: "+ bmi);
            }
            catch(Exception ee)
            {
                Toast.makeText(BMIActivity.this,"Invalid Values",Toast.LENGTH_LONG).show();
            }


        }
    }
    class chart1click implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {

            Intent i=new Intent(BMIActivity.this,DietPlanActivity.class);
            startActivity(i);
        }
        }

}