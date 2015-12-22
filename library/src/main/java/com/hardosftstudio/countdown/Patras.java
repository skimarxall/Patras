package com.hardosftstudio.countdown;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.hardosftstudio.countdown.util.PatrasCountDownTimer;

/**
 * Created by pintobie on 12/21/2015.
 */
public class Patras extends TextView implements PatrasActions {

    private PatrasCountDownTimer countDownTimer;

    private Animation switchInAnimation;
    private Animation switchOutAnimation;

    private int defaultCount;
    private int currentCount;

    private boolean autoStart = true;
    private boolean autoRemove = true;

    private PatrasCallback callback;
    private State state;

    public Patras(Context context) {
        this(context, null);
    }

    public Patras(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Patras(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Patras,
                0, 0);

        try {
            state = State.IDLE;
            currentCount = defaultCount = a.getInteger(R.styleable.Patras_startCount, 3);
            autoStart = a.getBoolean(R.styleable.Patras_autoStart, true);
            autoRemove = a.getBoolean(R.styleable.Patras_autoRemove, true);
            setSwitchInAnimation(a.getResourceId(R.styleable.Patras_switchInAnimation, android.R.anim.fade_in));
            setSwitchOutAnimation(a.getResourceId(R.styleable.Patras_switchOutAnimation, android.R.anim.fade_out));
        } finally {
            a.recycle();
        }

        setGravity(attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "gravity", Gravity.CENTER));
        setText(String.valueOf(defaultCount));

        if (autoStart) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    start();
                }
            });
        }
    }

    @Override
    public boolean start() {
        return start(defaultCount);
    }

    @Override
    public boolean start(int count) {
        if (countDownTimer != null || state == State.DETACHED) {
            return false;
        }
        this.currentCount = count;
        setText(String.valueOf(count));
        countDownTimer = new PatrasCountDownTimer(count * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                switchOutAnimation.setAnimationListener(new SwitchAnimationListener() {

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        currentCount--;
                        setText(String.valueOf(currentCount));
                        startAnimation(switchInAnimation);
                    }

                });
                startAnimation(switchOutAnimation);
            }

            @Override
            public void onFinish() {
                countDownTimer = null;
                switchOutAnimation.setAnimationListener(new SwitchAnimationListener() {

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        notifyStateChanged(State.FINISHED);
                        currentCount = defaultCount;
                        if (autoRemove) {
                            remove();
                        }
                    }
                });
                switchOutAnimation.setFillAfter(true);
                startAnimation(switchOutAnimation);
            }
        };
        countDownTimer.start();

        notifyStateChanged(State.RUNNING);
        return true;
    }

    @Override
    public boolean pause() {
        if (countDownTimer == null || state == State.PAUSED) {
            return false;
        }
        countDownTimer.cancel();
        countDownTimer = null;
        notifyStateChanged(State.PAUSED);
        return true;
    }

    @Override
    public boolean resume() {
        if (state == State.RUNNING || state == State.DETACHED) {
            return false;
        }

        return start(currentCount);
    }

    @Override
    public boolean stop() {
        if (countDownTimer == null || state == State.FINISHED) {
            return false;
        }
        countDownTimer.cancel();
        countDownTimer = null;
        currentCount = defaultCount;
        notifyStateChanged(State.FINISHED);
        return true;
    }

    @Override
    public void remove() {
        if (getParent() != null && getParent() instanceof ViewGroup) {
            ((ViewGroup) getParent()).removeView(this);
        }
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void setPatrasCallback(PatrasCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        state = State.DETACHED;
        stop();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        state = State.IDLE;
    }

    public void setSwitchInAnimation(int id) {
        switchInAnimation = AnimationUtils.loadAnimation(getContext(), id);
        if (switchInAnimation.getDuration() > 500) {
            throw new IllegalArgumentException("The in animation can't be bigger than 0.5s");
        }
    }

    public void setSwitchOutAnimation(int id) {
        switchOutAnimation = AnimationUtils.loadAnimation(getContext(), id);
        if (switchOutAnimation.getDuration() > 500) {
            throw new IllegalArgumentException("The in animation can't be bigger than 0.5s");
        }
    }

    public boolean isAutoRemove() {
        return autoRemove;
    }

    public void setAutoRemove(boolean autoRemove) {
        this.autoRemove = autoRemove;
    }

    public int getDefaultCount() {
        return defaultCount;
    }

    public void setDefaultCount(int defaultCount) {
        this.defaultCount = defaultCount;
    }

    public boolean isAutoStart() {
        return autoStart;
    }

    public void setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
    }

    private void notifyStateChanged(State state) {
        this.state = state;
        if (callback != null) {
            callback.onPatrasStateChanged(state);
        }
    }

    public interface PatrasCallback {
        void onPatrasStateChanged(State state);
    }

    public enum State {
        IDLE,
        RUNNING,
        FINISHED,
        PAUSED,
        DETACHED
    }

    private abstract class SwitchAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
