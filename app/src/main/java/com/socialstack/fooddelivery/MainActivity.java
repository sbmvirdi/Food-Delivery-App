package com.socialstack.fooddelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class MainActivity extends AppCompatActivity {
    private CardView domino,kitchen,oven,vascos,ccd,profile;
    private FirebaseAuth mAuth;

    private MaterialSpinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        if (mAuth.getCurrentUser() == null){
            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
        }

        domino = findViewById(R.id.domino_card);
        kitchen = findViewById(R.id.kitchen_card);
        oven = findViewById(R.id.oven_card);
        vascos = findViewById(R.id.vascos_card);
        ccd = findViewById(R.id.ccd_card);
        profile = findViewById(R.id.profile_m);
        spinner = findViewById(R.id.location);
        spinner.setItems("Select Location","Boys Hostel 1","Boys Hostel 2","Boys Hostel 3");
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
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });




    }
}
