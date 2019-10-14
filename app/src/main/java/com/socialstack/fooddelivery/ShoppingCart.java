package com.socialstack.fooddelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShoppingCart extends AppCompatActivity {
    private Button place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shopping_cart);
        place = findViewById(R.id.placeorder);
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShoppingCart.this,Success.class);
                startActivity(i);
                finish();
            }
        });

    }
}
