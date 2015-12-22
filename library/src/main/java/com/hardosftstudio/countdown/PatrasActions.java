package com.hardosftstudio.countdown;

/**
 * Created by pintobie on 12/22/2015.
 */
public interface PatrasActions {

    boolean start();

    boolean start(int count);

    boolean stop();

    boolean pause();

    boolean resume();

    void remove();

    Patras.State getState();

    void setPatrasCallback(Patras.PatrasCallback callback);

}
