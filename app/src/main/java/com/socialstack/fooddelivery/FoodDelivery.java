package com.socialstack.fooddelivery;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class FoodDelivery extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
