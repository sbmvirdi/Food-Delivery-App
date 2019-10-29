package com.socialstack.fooddelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class MainActivity extends AppCompatActivity {
    private CardView domino,kitchen,oven,vascos,ccd,profile;
    private FirebaseAuth mAuth;
    private String uid;
    private TextView name;
    private DatabaseReference isVendor;
    private boolean isitvendor;
    private ProgressDialog pd;

    private MaterialSpinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.greeting);
        pd = new ProgressDialog(this);
        pd.setTitle("Personalizing Dashboard");
        pd.setMessage("Please Wait...");
        pd.show();
        pd.setCancelable(false);
        if (mAuth.getCurrentUser() == null){
            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
        }
        else {
            uid = mAuth.getCurrentUser().getUid();
        }

        isVendor = FirebaseDatabase.getInstance().getReference().child("vendor").child(uid);
        isVendor.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                isitvendor = (boolean) dataSnapshot.getValue();
                //Toast.makeText(MainActivity.this, ""+isitvendor, Toast.LENGTH_SHORT).show();
                if (isitvendor){
                    pd.dismiss();
                    Intent i = new Intent(MainActivity.this,Vendor.class);
                    startActivity(i);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                }
                else {
                    pd.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        domino = findViewById(R.id.domino_card);
        kitchen = findViewById(R.id.kitchen_card);
        oven = findViewById(R.id.oven_card);
        vascos = findViewById(R.id.vascos_card);
        ccd = findViewById(R.id.ccd_card);
        profile = findViewById(R.id.profile_m);
        spinner = findViewById(R.id.location);
        spinner.setItems("Select Location","Boys Hostel 1","Boys Hostel 2","Boys Hostel 3");


        DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String greet = (String) dataSnapshot.child("name").getValue();
                name.setText("Hello "+greet+",");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        switch (position){
                            case 0:  oven.setVisibility(View.VISIBLE);
                                vascos.setVisibility(View.VISIBLE);
                                ccd.setVisibility(View.VISIBLE);
                                domino.setVisibility(View.VISIBLE);
                                kitchen.setVisibility(View.VISIBLE);
                                break;
                            case 1:  oven.setVisibility(View.GONE);
                                vascos.setVisibility(View.GONE);
                                ccd.setVisibility(View.GONE);
                                domino.setVisibility(View.VISIBLE);
                                kitchen.setVisibility(View.VISIBLE);
                                break;
                            case 2:  domino.setVisibility(View.GONE);
                                kitchen.setVisibility(View.GONE);
                                oven.setVisibility(View.VISIBLE);
                                vascos.setVisibility(View.VISIBLE);
                                ccd.setVisibility(View.GONE);
                                break;
                            case 3:   domino.setVisibility(View.GONE);
                                kitchen.setVisibility(View.GONE);
                                oven.setVisibility(View.GONE);
                                vascos.setVisibility(View.VISIBLE);
                                ccd.setVisibility(View.VISIBLE);

                        }
                    }
                });

            }
        });
        t.start();



        domino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,DetailFood.class);
                i.putExtra("shop","1");
                i.putExtra("name","Dominos");
                i.putExtra("tag","Delivering Pizza Since 1960");
                i.putExtra("hashtag1","Pizza");
                i.putExtra("hashtag2","Beverages");
                i.putExtra("rating","4.0");
                startActivity(i);

            }
        });
        kitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,DetailFood.class);
                i.putExtra("shop","2");
                i.putExtra("name","Kitchen Ette");
                i.putExtra("tag","Delivering Classy Food");
                i.putExtra("hashtag1","Thali");
                i.putExtra("hashtag2","Punjabi");
                i.putExtra("rating","4.5");
                startActivity(i);

            }
        });
        oven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,DetailFood.class);
                i.putExtra("shop","3");
                i.putExtra("name","Oven Express");
                i.putExtra("tag","Train to Deliver Food");
                i.putExtra("hashtag1","Rice Bowls");
                i.putExtra("hashtag2","Beverages");
                i.putExtra("rating","4.2");
                startActivity(i);

            }
        });
        vascos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,DetailFood.class);
                i.putExtra("shop","4");
                i.putExtra("name","Vascos");
                i.putExtra("tag","Anything that walks, swims, crawls, or flies with its back to heaven is edible");
                i.putExtra("hashtag1","Thai");
                i.putExtra("hashtag2","Chinese");
                i.putExtra("rating","4.7");
                startActivity(i);

            }
        });
        ccd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,DetailFood.class);
                i.putExtra("shop","5");
                i.putExtra("name","Caffe Coffee Day");
                i.putExtra("tag","A Lot can Happen over Coffee!");
                i.putExtra("hashtag1","Cappuccino");
                i.putExtra("hashtag2","Coffee Macha");
                i.putExtra("rating","3.9");
                startActivity(i);

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(i);
            }
        });


      /* admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AdminPortal.class);
                startActivity(i);
            }
        });

       */



    }
}
