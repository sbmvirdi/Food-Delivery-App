package com.socialstack.fooddelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    private Button signup,loginScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signup = findViewById(R.id.signup_now);
        loginScreen = findViewById(R.id.to_login_screen);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Signup.this, "Feature Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        loginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signup.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
}
