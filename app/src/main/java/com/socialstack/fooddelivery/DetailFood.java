package com.socialstack.fooddelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailFood extends AppCompatActivity {
    private ImageView shopicon;
    private TextView shopname,tag,rating,ht1,ht2;
    private String shop;
    private RecyclerView rec;
    private Query ref;
    private DatabaseReference df;
    private FirebaseAuth mAuth;
    private String uid;
    private Button gotocart;
    private int shopno=0;
    private DatabaseReference vendorupdate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food_dynamic);
        rec = findViewById(R.id.recyclerview);
        gotocart = findViewById(R.id.gotocart);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null){
            uid = mAuth.getCurrentUser().getUid();

        }
        else{
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
            finish();
        }

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


       //Log.e("USER",mAuth.getCurrentUser().getEmail()+"");




        if (shop.equals("1")) {
            shopno=1;
            shopicon.setImageResource(R.drawable.dominospizza);
            ref = FirebaseDatabase.getInstance().getReference().child("food").child(uid).child("shop1");
            df = FirebaseDatabase.getInstance().getReference().child("food").child(uid).child("shop1");
           // vendorupdate = FirebaseDatabase.getInstance().getReference().child("vendor_user").child("9iQMncHMoPYvtDt7LF0CzX44tK52");
            //Toast.makeText(this, ""+shop, Toast.LENGTH_SHORT).show();
        }
        else if (shop.equals("2")){
            shopno=2;
            shopicon.setImageResource(R.drawable.df2);
            ref = FirebaseDatabase.getInstance().getReference().child("food").child(uid).child("shop2");
            df = FirebaseDatabase.getInstance().getReference().child("food").child(uid).child("shop2");
           // vendorupdate = FirebaseDatabase.getInstance().getReference().child("vendor_user").child("3V6W0tzl6EX3RwqpULCVDVqzDhG2");

            // Toast.makeText(this, ""+shop, Toast.LENGTH_SHORT).show();

        }
        else if (shop.equals("3")){
            shopno=3;
            shopicon.setImageResource(R.drawable.df3);
            ref = FirebaseDatabase.getInstance().getReference().child("food").child(uid).child("shop3");
            df = FirebaseDatabase.getInstance().getReference().child("food").child(uid).child("shop3");
            //vendorupdate = FirebaseDatabase.getInstance().getReference().child("vendor_user").child("Vwm8Z0migHRX7CTMXcBStf2hOXV2");
            //Toast.makeText(this, ""+shop, Toast.LENGTH_SHORT).show();

        }
        else if (shop.equals("4")){
            shopno=4;
            shopicon.setImageResource(R.drawable.df4);
            ref = FirebaseDatabase.getInstance().getReference().child("food").child(uid).child("shop4");
            df = FirebaseDatabase.getInstance().getReference().child("food").child(uid).child("shop4");
            //Toast.makeText(this, ""+shop, Toast.LENGTH_SHORT).show();
            //vendorupdate = FirebaseDatabase.getInstance().getReference().child("vendor_user").child("2nXgGaDHJBVdo1fGfR9vffdHmj12");


        }
        else if (shop.equals("5")){
            shopno=5;
            shopicon.setImageResource(R.drawable.df5);
            ref = FirebaseDatabase.getInstance().getReference().child("food").child(uid).child("shop5");
            df = FirebaseDatabase.getInstance().getReference().child("food").child(uid).child("shop5");
            //Toast.makeText(this, ""+shop, Toast.LENGTH_SHORT).show();
            //vendorupdate = FirebaseDatabase.getInstance().getReference().child("vendor_user").child("AcFgULvMoFgYPlruHLpyN7U9gQC3");

        }

         ref.keepSynced(true);
        // recycler view implementation

        rec.setLayoutManager(new GridLayoutManager(DetailFood.this,2));
        FirebaseRecyclerOptions firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<itemmodel>().setQuery(ref,itemmodel.class).build();
        FirebaseRecyclerAdapter<itemmodel,myviewholder> adapter = new FirebaseRecyclerAdapter<itemmodel, myviewholder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull myviewholder myviewholder, int i, @NonNull itemmodel itemmodel) {
                myviewholder.setData(itemmodel.getName(),itemmodel.getImage(),itemmodel.getPrice());
                myviewholder.setAdd(itemmodel.isClicked());
                myviewholder.getid(itemmodel.getId());
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
        adapter.notifyDataSetChanged();


        try {
            adapter.startListening();
        }catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        gotocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailFood.this,ShoppingCart.class);
                switch (shopno){
                    case 1: i.putExtra("uid_shop","9iQMncHMoPYvtDt7LF0CzX44tK52");
                            break;
                    case 2: i.putExtra("uid_shop","3V6W0tzl6EX3RwqpULCVDVqzDhG2");
                        break;
                    case 3: i.putExtra("uid_shop","Vwm8Z0migHRX7CTMXcBStf2hOXV2");
                        break;
                    case 4: i.putExtra("uid_shop","2nXgGaDHJBVdo1fGfR9vffdHmj12");
                        break;
                    case 5: i.putExtra("uid_shop","xLz8awmzqIdDzCJZ95XcHBs7XUz1");
                        break;

                }
                startActivity(i);
            }
        });



    }

    public class myviewholder extends RecyclerView.ViewHolder{
        private TextView name,price;
        private Button add;
        private ImageView pic;
        private long price_long;
        private String id,image,name_s;
        private boolean clicked;
        private boolean button_clicked;
        View v;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
            name = v.findViewById(R.id.fooditemname);
            add = v.findViewById(R.id.add1);
            pic = v.findViewById(R.id.foodpic1);
            price = v.findViewById(R.id.fooditemprice);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.e("ID",id);
                 df.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         price_long = (long) dataSnapshot.child("price").getValue();
                         //Toast.makeText(DetailFood.this, ""+price_long, Toast.LENGTH_SHORT).show();
                         button_clicked = (boolean) dataSnapshot.child("clicked").getValue();



                         if (button_clicked) {

                            // Toast.makeText(DetailFood.this, ""+clicked, Toast.LENGTH_SHORT).show();
                             df.child(id).child("clicked").setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if (task.isSuccessful()){
                                        // Toast.makeText(DetailFood.this, "Data Written false", Toast.LENGTH_SHORT).show();

                                         DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("cart").child(uid).child(id);
                                         d.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                             @Override
                                             public void onComplete(@NonNull Task<Void> task) {
                                                 if (task.isSuccessful()){
                                                     Toast.makeText(DetailFood.this, "removed from cart!", Toast.LENGTH_SHORT).show();

                                                 }
                                                 else
                                                 {
                                                     Toast.makeText(DetailFood.this, "failed to remove from cart!", Toast.LENGTH_SHORT).show();
                                                 }
                                             }
                                         });

                                     }
                                     else{
                                         Toast.makeText(DetailFood.this, "failed to write!", Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             });

                         }else{
                             df.child(id).child("clicked").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if (task.isSuccessful()){
                                         Toast.makeText(DetailFood.this, "Data Written!", Toast.LENGTH_SHORT).show();

                                         Map<String,Object> map = new HashMap<>();
                                         map.put(id, new itemmodel(name_s,image,!clicked,price_long,id));
                                         DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("cart").child(uid);
                                         d.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                             @Override
                                             public void onComplete(@NonNull Task<Void> task) {
                                                 if (task.isSuccessful()){
                                                     Toast.makeText(DetailFood.this, "added to cart!", Toast.LENGTH_SHORT).show();
                                                 }
                                                 else{
                                                     Toast.makeText(DetailFood.this, "failed to add!", Toast.LENGTH_SHORT).show();
                                                 }
                                             }
                                         });
                                     }
                                     else{
                                         Toast.makeText(DetailFood.this, "failed to write!", Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             });
                         }

                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });




                }
            });
        }

        private void setData(String name,String image,long price){
            this.name_s = name;
            this.image = image;
            this.name.setText(name);
            Picasso.get().load(image).into(pic);
            this.price.setText("Rs "+price);
        }

        private void setAdd(boolean set){
            this.clicked = set;
            if (set){
                add.setText("Remove");
                add.setBackground(getResources().getDrawable(R.drawable.redcircle));
            }else{
                add.setText("Add");
                add.setBackground(getResources().getDrawable(R.drawable.greencircle1));
            }
        }
        private void getid(String id){
            this.id = id;
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        ArrayList<String> child = new ArrayList<>();
        child.add("msdjhdsasda");
        child.add("nasdjkndsak");
        child.add("osadjhdsav");
        child.add("pdsavjds");
        ArrayList<String> shops = new ArrayList<>();
        shops.add("shop1");
        shops.add("shop2");
        shops.add("shop3");
        shops.add("shop4");
        shops.add("shop5");

        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("cart").child(uid);
        DatabaseReference df1 = FirebaseDatabase.getInstance().getReference().child("food").child(uid);
        for (String shop:shops) {
            for (String mchild : child) {

                df1.child(shop).child(mchild).child("clicked").setValue(false);

            }

        }

        df.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
             if (task.isSuccessful()){
                 Toast.makeText(DetailFood.this, "Cart Emptied!", Toast.LENGTH_SHORT).show();
             }
            }
        });
    }


}
