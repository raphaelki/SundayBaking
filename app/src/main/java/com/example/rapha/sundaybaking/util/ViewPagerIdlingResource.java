package com.example.rapha.sundaybaking.util;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class ViewPagerIdlingResource implements IdlingResource {

    private ResourceCallback resourceCallback;
    private AtomicBoolean mIdle = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIdle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resourceCallback = callback;
    }

    public void setIdleState(boolean isIdleNow) {
        mIdle.set(isIdleNow);
        if (isIdleNow && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
    }
}
