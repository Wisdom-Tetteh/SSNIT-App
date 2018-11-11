package com.tetteh1568.breadblog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SingleFoodActivity extends AppCompatActivity {

    private String food_key = null;
    private DatabaseReference mDatabase;
    private  DatabaseReference userData;
    private TextView singleFoodTitle,singleFoodDesc,singleFoodPrice;
    private ImageView singleFoodImage;
    private FirebaseAuth mAuth;
    private FirebaseUser current_user;
    private DatabaseReference mRef;
    private String food_name,food_price,food_desc,food_image;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);
        mProgress = new ProgressDialog(this);
        food_key = getIntent().getExtras().getString("FoodId");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Item");

        singleFoodImage = (ImageView) findViewById(R.id.singleImageView);
        singleFoodTitle =(TextView) findViewById(R.id.singleTitle);
        singleFoodDesc = (TextView) findViewById(R.id.singleDesc);
        singleFoodPrice = (TextView) findViewById(R.id.singlePrice);

        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getCurrentUser();
        userData = FirebaseDatabase.getInstance().getReference().child("users").child(current_user.getUid());
        mRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        mDatabase.child(food_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                food_desc = (String) dataSnapshot.child("desc").getValue();
                food_image = (String) dataSnapshot.child("image").getValue();
                food_name = (String) dataSnapshot.child("name").getValue();
                food_price = (String) dataSnapshot.child("price").getValue();




                singleFoodDesc.setText(food_desc);
                Picasso.with(SingleFoodActivity.this).load(food_image).into(singleFoodImage);
                singleFoodTitle.setText(food_name);
                singleFoodPrice.setText(food_price);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });}
    public void orderItemClicked(View view){
        mProgress.setMessage("Placing Your Order......Please Wait");
        mProgress.show();
        final DatabaseReference newOrder = mRef.push();
        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newOrder.child("itemname").setValue(food_name);
                newOrder.child("username").setValue(dataSnapshot.child("Name").getValue()).addOnCompleteListener(new OnCompleteListener<Void> ()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(SingleFoodActivity.this,Dashboard.class));
                        mProgress.dismiss();

                        Toast.makeText(SingleFoodActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SingleFoodActivity.this, "Order Failed...Check Internet Connectivity!!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}

