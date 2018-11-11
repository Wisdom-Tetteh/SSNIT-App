package com.tetteh1568.breadblog;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText email,pass,name;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mProgress = new ProgressDialog(this);
        email = (EditText) findViewById(R.id.editEmail);
        pass =   (EditText) findViewById(R.id.editPass);
        name = (EditText) findViewById(R.id.editName);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");


    }

    public void signupButtonClicked(View view) {

        final String email_text = email.getText().toString().trim();
        String pass_text = pass.getText().toString().trim();
        String name_text = name.getText().toString().trim();

        if (!TextUtils.isEmpty(email_text) && !TextUtils.isEmpty(pass_text) && !TextUtils.isEmpty(name_text)){
            mProgress.setMessage("Creating SSNIT Account...");
            mProgress.show();
            mAuth.createUserWithEmailAndPassword(email_text,pass_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user = mDatabase.child(user_id);
                        current_user.child("Name").setValue(email_text);

                        Intent login = new Intent(Register.this,LoginActivity.class);
                        startActivity(login);
                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mProgress.dismiss();
                        Toast.makeText(Register.this, "Signed Up Successful!!!", Toast.LENGTH_SHORT).show();

                    }else if (task.getException()instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(Register.this, "SSNIT account already exists..Log In", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(Register.this, "Check Internet Connectivity, Error in creating account..Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void signinButtonClicked(View view){
        Intent loginIntent = new Intent(Register.this,LoginActivity.class);
        startActivity(loginIntent);
    }

    public void contactButtonClicked(View view){
        Intent contactIntent = new Intent(Register.this,contact.class);
        startActivity(contactIntent);
    }

    public void aboutButtonClicked(View view){
        Intent aboutIntent = new Intent(Register.this,About.class);
        startActivity(aboutIntent);
    }



}

