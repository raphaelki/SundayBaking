package com.example.rapha.sundaybaking.ui.common;

import android.app.Activity;
import android.content.pm.ActivityInfo;

public class ActivityHelper {

    public static void setOrientationToPortraitMode(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
