package com.hardosftstudio.countdown.util;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pintobie on 12/22/2015.
 */
public class ViewFinder {

    public static <T> T findView(View view, Class<T> tClass) {
        if (tClass.isInstance(view)) {
            return tClass.cast(view);
        } else if (!(view instanceof ViewGroup)) {
            return null;
        }
        int count = ((ViewGroup) view).getChildCount();
        for (int i = 0; i < count; i++) {
            View child = ((ViewGroup) view).getChildAt(i);
            if ((tClass.isInstance(child))) {
                return tClass.cast(child);
            }
            T rChild = findView(child, tClass);
            if (rChild != null) {
                return rChild;
            }
        }
        return null;
    }

}
