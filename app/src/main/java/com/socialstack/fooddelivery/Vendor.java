package com.socialstack.fooddelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Vendor extends AppCompatActivity {
    private String uid;
    private FirebaseAuth mAuth;
    private Button mLogout;
    private RecyclerView vendorrec;
    private Query ref;
    private DatabaseReference name_df;
    private String name;
    private TextView greetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);
        mAuth  = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null){
            uid = mAuth.getCurrentUser().getUid();
        }else {
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
            finish();
        }
        greetings = findViewById(R.id.vendorgreeting);
        ref = FirebaseDatabase.getInstance().getReference().child("vendor_user").child(uid).child("orders").orderByChild("code");
        name_df  = FirebaseDatabase.getInstance().getReference().child("vendor_user").child(uid);
        vendorrec = findViewById(R.id.vendorrec);
        vendorrec.setLayoutManager(new LinearLayoutManager(this));
        mLogout = findViewById(R.id.vendorlogout);
        name_df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = (String) dataSnapshot.child("name").getValue();
                greetings.setText("Hello "+name+"!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(Vendor.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


        FirebaseRecyclerOptions firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<VendorModel>().setQuery(ref,VendorModel.class).build();
        FirebaseRecyclerAdapter<VendorModel,vendorviewholder> adapter = new FirebaseRecyclerAdapter<VendorModel, vendorviewholder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull vendorviewholder vendorviewholder, int i, @NonNull VendorModel vendorModel) {
            vendorviewholder.setdata(vendorModel.getOrderid(),vendorModel.getAmount(),vendorModel.getItems());

            }

            @NonNull
            @Override
            public vendorviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v;
                v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.vendoritemrow,parent,false);
                return new vendorviewholder(v);
            }
        };
            vendorrec.setAdapter(adapter);

        try {
            adapter.startListening();
        }catch (Exception e){
            Log.e("ADAPTER EXCEPTION",e.getMessage()+"");
        }




    }

    public class vendorviewholder extends RecyclerView.ViewHolder{
        private TextView orderid,amount,items;
        private View view;
        public vendorviewholder(@NonNull View itemView) {
            super(itemView);
            view  = itemView;
            orderid = view.findViewById(R.id.orderid_vendor);
            amount = view.findViewById(R.id.orderamount_vendor);
            items = view.findViewById(R.id.orderitem_vendor);
        }

        private void setdata(long order_id,long amount_,String items_){
            orderid.setText(order_id+"");
            amount.setText(amount_+"");
            items.setText(items_);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(this, "Kindly logout first!", Toast.LENGTH_SHORT).show();


    }
}
