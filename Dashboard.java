package com.tetteh1568.breadblog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void claimsButtonClicked(View view) {
        Intent claimsIntent = new Intent(Dashboard.this, claim.class);
        startActivity(claimsIntent);
    }


    public void aboutButtonClicked(View view) {
        Intent aboutButtonClicked = new Intent(Dashboard.this, About.class);
        startActivity(aboutButtonClicked);
    }

    public void complaintsButtonClicked(View view) {
        Intent complaintsButtonClicked = new Intent(Dashboard.this, complaints.class);
        startActivity(complaintsButtonClicked);

    }

    public void contactButtonClicked(View view) {
        Intent contactButtonClicked = new Intent(Dashboard.this, contact.class);
        startActivity(contactButtonClicked);
    }

    public void accountButtonClicked(View view) {
        Intent accountButtonClicked = new Intent(Dashboard.this, Daccounts.class);
        startActivity(accountButtonClicked);

    }

}