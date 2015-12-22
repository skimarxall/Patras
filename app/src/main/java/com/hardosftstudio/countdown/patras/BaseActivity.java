package com.hardosftstudio.countdown.patras;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pintobie on 12/22/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewGroup viewPlaceholder = (ViewGroup) findViewById(R.id.view_placeholder);
        viewPlaceholder.addView(LayoutInflater.from(this).inflate(getPlaceHolderView(), viewPlaceholder, false));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabClick((FloatingActionButton) view);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setFabImage(int id) {
        fab.setImageResource(id);
    }

    public abstract int getPlaceHolderView();

    public abstract void onFabClick(FloatingActionButton fab);
}
