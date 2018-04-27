package com.example.rapha.sundaybaking.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.rapha.sundaybaking.util.Constants;

import timber.log.Timber;

public class IngredientWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences sharedPref = context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        String recipeName = sharedPref.getString(Constants.RECIPE_NAME_KEY, "");
        RemoteViews views = WidgetConfigActivity.setupWidget(context, recipeName, appWidgetId);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Timber.d("Widget onUpdate");
        // There may be multiple widgets active, so update all of them
        // called for essential setup
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

