package com.socialstack.fooddelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AppCompatActivity {
    private Button place;
    private RecyclerView rec;
    private Query ref;
    private FirebaseAuth mAuth;
    private String uid;
    private List<CartModel> cartdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        mAuth = FirebaseAuth.getInstance();
        place = findViewById(R.id.placeorder);
        if (mAuth.getCurrentUser()!=null){
            uid = mAuth.getCurrentUser().getUid();
        }
        rec = findViewById(R.id.cartrec);
        ref = FirebaseDatabase.getInstance().getReference().child("cart").child(uid);

        cartdata = new ArrayList<>();

        rec.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<CartModel>().setQuery(ref,CartModel.class).build();
        FirebaseRecyclerAdapter<CartModel,cartholder> adapter = new FirebaseRecyclerAdapter<CartModel, cartholder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull cartholder cartholder, int i, @NonNull CartModel cartModel) {
                cartholder.setdata(cartModel.getName(),cartModel.getPrice(),cartModel.getImage());
                cartdata.add(cartModel);
            }

            @NonNull
            @Override
            public cartholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View c;
                c = LayoutInflater.from(getApplicationContext()).inflate(R.layout.cartitem,parent,false);
                return new cartholder(c);
            }
        };


        rec.setAdapter(adapter);
        try {
            adapter.startListening();
        }catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();


        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                df.push().setValue(cartdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                     if (task.isSuccessful()){
                         Toast.makeText(ShoppingCart.this, "Order Placed", Toast.LENGTH_SHORT).show();
                         DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("cart").child(uid);
                         d.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 if (task.isSuccessful()) {

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

                                     DatabaseReference df1 = FirebaseDatabase.getInstance().getReference().child("food").child(uid);
                                     for (String shop : shops) {
                                         for (String mchild : child) {

                                             df1.child(shop).child(mchild).child("clicked").setValue(false);

                                         }

                                         Toast.makeText(ShoppingCart.this, "Cart khali ho gya!", Toast.LENGTH_SHORT).show();
                                         Intent i = new Intent(ShoppingCart.this, Success.class);
                                         startActivity(i);
                                         finish();
                                     }
                                 }
                             }
                     });
                    }
                }

            });
        }


    });
    }

    public class cartholder extends RecyclerView.ViewHolder{
        private TextView name,price;
        private ImageView image;
        private View v;

        public cartholder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
            name = v.findViewById(R.id.shopname);
            price = v.findViewById(R.id.shopprice);
            image = v.findViewById(R.id.shop1id);
        }

        private void setdata(String name,long price,String image){
            this.name.setText(name);
            this.price.setText("Rs:: "+price);
            Picasso.get().load(image).into(this.image);

        }
    }
}
