package com.example.rapha.sundaybaking.ui.instructions;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.res.Configuration;
import android.os.Handler;
import android.view.View;

public class PlayerToolbarComponent implements LifecycleObserver {

    private Handler handler;
    private View decorView;
    private int orientation;
    private boolean isTablet;

    public PlayerToolbarComponent(View decorView, boolean isTablet, int orientation) {
        this.decorView = decorView;
        this.isTablet = isTablet;
        this.orientation = orientation;
        handler = new Handler();
        decorView.setOnSystemUiVisibilityChangeListener
                (visibility -> {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        hideSystemBarsInLandscapeMode(3);
                    }
                });
    }

    private void hideSystemUI() {
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void configureFullScreenModeForLandscapeMode() {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTablet) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void onPause() {
        handler.removeCallbacksAndMessages(null);
    }

    private void hideSystemBarsInLandscapeMode(int delayInSec) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTablet) {
            Runnable hideSystemUIRunnable = this::hideSystemUI;
            handler.postDelayed(hideSystemUIRunnable, delayInSec * 1000);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume() {
        configureFullScreenModeForLandscapeMode();
        hideSystemBarsInLandscapeMode(3);
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
