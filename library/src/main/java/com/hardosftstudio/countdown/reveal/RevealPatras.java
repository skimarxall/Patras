package com.hardosftstudio.countdown.reveal;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;

import com.hardosftstudio.countdown.Patras;
import com.hardosftstudio.countdown.PatrasActions;
import com.hardosftstudio.countdown.util.ViewFinder;

/**
 * Created by pintobie on 12/21/2015.
 */
public class RevealPatras extends RevealLayout implements Patras.PatrasCallback, PatrasActions {

    private Patras patras;
    private Patras.PatrasCallback patrasCallback;
    private boolean isAutoStart;

    public RevealPatras(Context context) {
        this(context, null);
    }

    public RevealPatras(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RevealPatras(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setContentShown(false);
        setClipRadius(0);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                if (isAutoStart) {
                    show();
                }
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (patras == null) {
            this.patras = ViewFinder.findView(this, Patras.class);
            if (patras == null) {
                throw new IllegalStateException("This view must contains a Patras view!");
            }

            isAutoStart = patras.isAutoStart();
            patras.setAutoStart(false);
            patras.setAutoRemove(false);
            patras.setPatrasCallback(this);
        }
    }

    @Override
    public void onPatrasStateChanged(Patras.State state) {
        if (patrasCallback != null) {
            patrasCallback.onPatrasStateChanged(state);
        }
    }

    @Override
    public void show(int x, int y, int duration, @Nullable final Animation.AnimationListener listener) {
        if (patras != null) {
            patras.start();
        }
        super.show(x, y, duration, listener);
    }

    @Override
    public void hide(int x, int y, int duration, @Nullable Animation.AnimationListener listener) {
        if (patras != null) {
            patras.stop();
        }
        super.hide(x, y, duration, listener);
    }

    @Override
    public boolean start() {
        if (patras == null) return false;
        return patras.start();
    }

    @Override
    public boolean start(int count) {
        if (patras == null) return false;
        return patras.start(count);
    }

    @Override
    public boolean stop() {
        if (patras == null) return false;
        return patras.stop();
    }

    @Override
    public boolean pause() {
        if (patras == null) return false;
        return patras.pause();
    }

    @Override
    public boolean resume() {
        if (patras == null) return false;
        return patras.resume();
    }

    @Override
    public void remove() {
        if (patras == null) return;
        patras.remove();
    }

    @Override
    public Patras.State getState() {
        if (patras == null) return Patras.State.DETACHED;
        return patras.getState();
    }

    @Override
    public void setPatrasCallback(Patras.PatrasCallback patrasCallback) {
        this.patrasCallback = patrasCallback;
    }
}
