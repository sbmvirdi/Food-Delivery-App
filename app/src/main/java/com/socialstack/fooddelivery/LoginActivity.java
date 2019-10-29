package com.socialstack.fooddelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private String username,password;
    private EditText user,pass;
    private Button login,signup;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.login_now);
        signup = findViewById(R.id.sign_up_screen);
        user = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        pd = new ProgressDialog(this);
        if (mAuth.getCurrentUser()!=null){
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }

        pd.setTitle("Signing in");
        pd.setMessage("Please Wait ...");
        pd.setCancelable(false);
        pd.hide();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                username = user.getText().toString().trim();
                password = pass.getText().toString().trim();
                if (!TextUtils.isEmpty(username)|| !TextUtils.isEmpty(password)){
                   mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()){
                              pd.dismiss();
                              Toast.makeText(LoginActivity.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                              Intent i = new Intent(LoginActivity.this,MainActivity.class);
                              startActivity(i);
                              i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                              finish();
                          }else{
                              pd.dismiss();
                              Toast.makeText(LoginActivity.this, "An error occured!", Toast.LENGTH_SHORT).show();
                          }
                       }
                   });

                }
                else{
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, "Enter all the details!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this,Signup.class);
                startActivity(i);
                finish();
            }
        });

    }
}
