package com.hardosftstudio.countdown.patras;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import com.hardosftstudio.countdown.Patras;
import com.hardosftstudio.countdown.reveal.RevealPatras;

/**
 * Created by pintobie on 12/22/2015.
 */
public class RevealActivity extends BaseActivity implements Patras.PatrasCallback {

    private RevealPatras revealPatras;
    private TextView revealInfoText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        revealPatras = (RevealPatras) findViewById(R.id.reveal);
        revealInfoText = (TextView) findViewById(R.id.reveal_info_text);
        revealPatras.setPatrasCallback(this);
    }

    @Override
    public int getPlaceHolderView() {
        return R.layout.content_reveal;
    }

    @Override
    public void onFabClick(FloatingActionButton fab) {
        if (revealPatras.isContentShown()) {
            revealPatras.hide((int) fab.getX() + fab.getWidth()/2, getYPosition(fab));
        } else {
            revealPatras.show((int) fab.getX() + fab.getWidth()/2, getYPosition(fab));
        }
    }

    @Override
    public void onPatrasStateChanged(Patras.State state) {
        revealInfoText.setText(state.name());
        switch (state) {
            case RUNNING:
                setFabImage(R.drawable.ic_pause);
                break;
            case FINISHED:
                revealPatras.hide((int) fab.getX() + fab.getWidth()/2, getYPosition(fab));
            default:
                setFabImage(R.drawable.ic_action_play);
                break;
        }
    }

    private int getYPosition(View view) {
        int viewY = (int) view.getY();
        int revealHeight = revealPatras.getHeight();
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return viewY - (point.y - revealHeight) + view.getHeight()/2;
    }
}
