package com.hardosftstudio.countdown.patras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onPatras(View view) {
        startActivity(new Intent(this, CountdownActivity.class));
    }


    public void onReveal(View view) {
        startActivity(new Intent(this, RevealActivity.class));
    }

}
