package com.hardosftstudio.countdown.patras;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.hardosftstudio.countdown.Patras;

public class CountdownActivity extends BaseActivity implements Patras.PatrasCallback {

    private Patras patras;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patras = (Patras) findViewById(R.id.counter);
        patras.setPatrasCallback(this);
    }

    @Override
    public int getPlaceHolderView() {
        return R.layout.content_patras;
    }

    @Override
    public void onFabClick(FloatingActionButton fab) {
        if (!patras.resume()) {
            patras.pause();
        }
    }

    @Override
    public void onPatrasStateChanged(Patras.State state) {
        switch (state) {
            case RUNNING:
                setFabImage(R.drawable.ic_pause);
                break;
            default:
                setFabImage(R.drawable.ic_action_play);
                break;
        }
    }

}
