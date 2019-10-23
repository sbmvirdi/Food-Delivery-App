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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Signup extends AppCompatActivity {

    private Button signup,loginScreen;
    private EditText mName,mEmail,mPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signup = findViewById(R.id.signup_now);
        mName = findViewById(R.id.name_signup);
        mEmail = findViewById(R.id.email_signup);
        mPassword = findViewById(R.id.pass_signup);
        loginScreen = findViewById(R.id.to_login_screen);
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait ...");
        if (mAuth.getCurrentUser()!=null){
            Intent i = new Intent(Signup.this,MainActivity.class);
            startActivity(i);
            finish();

        }
        pd.hide();
        pd.setTitle("Signing Up");
        pd.setCancelable(false);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                //Toast.makeText(Signup.this, "initiated", Toast.LENGTH_SHORT).show();
                final String name = mName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String pass = mPassword.getText().toString().trim();

                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass) || !TextUtils.isEmpty(name)){
                    if (pass.length() >10) {

                        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Signup.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                                    String uid = mAuth.getCurrentUser().getUid();
                                    DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                                    d.child("name").setValue(name);
                                    DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("vendor").child(uid);
                                    r.setValue(false);
                                    initialsetup(uid);
                                    pd.dismiss();
                                    Intent i = new Intent(Signup.this, MainActivity.class);
                                    startActivity(i);
                                    finish();

                                }
                            }
                        });
                    }
                    else {
                        pd.dismiss();
                        Toast.makeText(Signup.this, "Length of Password should be greater than 10", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    pd.dismiss();
                    Toast.makeText(Signup.this, "Enter all the details", Toast.LENGTH_SHORT).show();
                }
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

    public void initialsetup(String uid){


        Map<String,Object> shop = new HashMap<>();
        shop.put("msdjhdsasda",new itemmodel("Corn Pizza","https://firebasestorage.googleapis.com/v0/b/food-delivery-48387.appspot.com/o/pizza1.png?alt=media&token=c4662c98-7f91-4fa9-a293-36dc7e7389cf",false,120,"msdjhdsasda"));
        shop.put("nasdjkndsak",new itemmodel("Onion Pizza","https://firebasestorage.googleapis.com/v0/b/food-delivery-48387.appspot.com/o/pizza2.png?alt=media&token=d595b7c6-adbb-4eac-a23b-812c6604a6bc",false,60,"nasdjkndsak"));
        shop.put("osadjhdsav",new itemmodel("Paperoni Pizza","https://firebasestorage.googleapis.com/v0/b/food-delivery-48387.appspot.com/o/pizza3.png?alt=media&token=e2763ad1-c3c0-4ccd-a67c-d0d9825012aa",false,80,"osadjhdsav"));
        shop.put("pdsavjds",new itemmodel("Veg Loaded","https://firebasestorage.googleapis.com/v0/b/food-delivery-48387.appspot.com/o/pizza4.png?alt=media&token=414aa269-6eae-430a-8d13-a13a405e5a97",false,130,"pdsavjds"));


        Map<String,Object> shop1 = new HashMap<>();
        shop1.put("msdjhdsasda",new itemmodel("Rice Bowl","https://firebasestorage.googleapis.com/v0/b/food-delivery-48387.appspot.com/o/ricebowl.png?alt=media&token=f7bf93fc-7c66-4bee-8c49-e7b1c3a3c275",false,50,"msdjhdsasda"));
        shop1.put("nasdjkndsak",new itemmodel("Noodles","https://firebasestorage.googleapis.com/v0/b/food-delivery-48387.appspot.com/o/noodles.png?alt=media&token=95abe438-e58d-431c-b025-d7775e8728f3",false,70,"nasdjkndsak"));
        shop1.put("osadjhdsav",new itemmodel("Soft Drinks","https://firebasestorage.googleapis.com/v0/b/food-delivery-48387.appspot.com/o/drink.png?alt=media&token=4b42500b-fcbe-45c0-b67b-16f899410507",false,40,"osadjhdsav"));
        shop1.put("pdsavjds",new itemmodel("Manchurian","https://firebasestorage.googleapis.com/v0/b/food-delivery-48387.appspot.com/o/Steaming.png?alt=media&token=ac01db1e-db4e-4a79-a5a0-22b06c315190",false,100,"pdsavjds"));


        DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("food");
        d.child(uid).child("shop1").updateChildren(shop);
        d.child(uid).child("shop2").updateChildren(shop1);
        d.child(uid).child("shop3").updateChildren(shop1);
        d.child(uid).child("shop4").updateChildren(shop1);
        d.child(uid).child("shop5").updateChildren(shop1);


    }
}
