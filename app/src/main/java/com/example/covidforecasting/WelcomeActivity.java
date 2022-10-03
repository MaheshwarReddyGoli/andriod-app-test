package com.example.covidforecasting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    Button Login;
    Button SignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Login=(Button)findViewById(R.id.Login);
        Login.setOnClickListener(new Loginclick ());
        SignUp=(Button)findViewById(R.id.SignUp);
        SignUp.setOnClickListener(new SignUpclick ());

    }
    class Loginclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {

            Intent i=new Intent(WelcomeActivity.this,LoginActivity.class);
            startActivity(i);
        }
    }

    class SignUpclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {

            Intent i=new Intent(WelcomeActivity.this,RegisterActivity.class);
            startActivity(i);
        }
    }
}