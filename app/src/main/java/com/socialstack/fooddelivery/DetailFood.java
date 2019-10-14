package com.socialstack.fooddelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class DetailFood extends AppCompatActivity {
    private ImageView shopicon;
    private TextView shopname,tag,rating,ht1,ht2;
    private String shop;
    private RecyclerView rec;
    private Query ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food_dynamic);
        rec = findViewById(R.id.recyclerview);

        shopicon = findViewById(R.id.shopicon);
        shopname = findViewById(R.id.shopname_c);
        tag = findViewById(R.id.speciality);
        rating = findViewById(R.id.rating_c);
        ht1 = findViewById(R.id.hashtag1);
        ht2 = findViewById(R.id.hashtag2);
        Bundle b = getIntent().getExtras();
        shop = b.getString("shop");
        shopname.setText(b.getString("name"));
        tag.setText(b.getString("tag"));
        rating.setText(b.getString("rating"));
        ht1.setText(b.getString("hashtag1"));
        ht2.setText(b.getString("hashtag2"));









        if (shop.equals("1")) {
            shopicon.setImageResource(R.drawable.dominospizza);
            ref = FirebaseDatabase.getInstance().getReference().child("food").child("shop1");

        }
        else if (shop.equals("2")){
            shopicon.setImageResource(R.drawable.df2);
            ref = FirebaseDatabase.getInstance().getReference().child("food").child("shop2");        }
        else if (shop.equals("3")){
            shopicon.setImageResource(R.drawable.df3);
            ref = FirebaseDatabase.getInstance().getReference().child("food").child("shop3");
        }
        else if (shop.equals("4")){
            shopicon.setImageResource(R.drawable.df4);
            ref = FirebaseDatabase.getInstance().getReference().child("food").child("shop4");
        }
        else if (shop.equals("5")){
            shopicon.setImageResource(R.drawable.df5);
            ref = FirebaseDatabase.getInstance().getReference().child("food").child("shop5");
        }

         ref.keepSynced(true);
        // recycler view implementation

        rec.setLayoutManager(new GridLayoutManager(DetailFood.this,2));
        FirebaseRecyclerOptions firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<itemmodel>().setQuery(ref,itemmodel.class).build();
        FirebaseRecyclerAdapter<itemmodel,myviewholder> adapter = new FirebaseRecyclerAdapter<itemmodel, myviewholder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull myviewholder myviewholder, int i, @NonNull itemmodel itemmodel) {
                myviewholder.setData(itemmodel.getName(),itemmodel.getImage());

            }

            @NonNull
            @Override
            public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v;
                v= LayoutInflater.from(getApplicationContext()).inflate(R.layout.fooditem,parent,false);
                return new myviewholder(v);
            }
        };
        rec.setAdapter(adapter);


        try {
            adapter.startListening();
        }catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

    public class myviewholder extends RecyclerView.ViewHolder{
        private TextView name;
        private Button add;
        private ImageView pic;
        View v;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
            name = v.findViewById(R.id.fooditemname);
            add = v.findViewById(R.id.add1);
            pic = v.findViewById(R.id.foodpic1);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DetailFood.this, "add clicked!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void setData(String name,String image){
            this.name.setText(name);
            Picasso.get().load(image).into(pic);
        }
    }

}
