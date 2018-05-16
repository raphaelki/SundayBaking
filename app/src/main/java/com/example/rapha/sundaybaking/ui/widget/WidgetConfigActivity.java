package com.example.rapha.sundaybaking.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.SundayBakingApp;
import com.example.rapha.sundaybaking.data.RecipeRepository;
import com.example.rapha.sundaybaking.databinding.ActivityWidgetConfigBinding;
import com.example.rapha.sundaybaking.ui.details.RecipesDetailsActivity;
import com.example.rapha.sundaybaking.ui.recipes.RecipeClickCallback;
import com.example.rapha.sundaybaking.util.Constants;

import timber.log.Timber;

public class WidgetConfigActivity extends AppCompatActivity implements RecipeClickCallback {

    private int widgetId;
    private RecipeRepository repository;

    public static RemoteViews setupWidget(Context context, String recipeName, int widgetId) {
        Timber.d("Updating widget for recipe: %s", recipeName);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingeredients);

        // setup RemoteViewsService for ingredient ListView
        Intent serviceIntent = new Intent(context, WidgetRemoteViewsService.class);
        serviceIntent.putExtra(Constants.RECIPE_NAME_KEY, recipeName);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        views.setRemoteAdapter(R.id.widget_ingredient_listview, serviceIntent);

        // setup Intent for "Show Recipe" button
        Intent showRecipeIntent = new Intent(context, RecipesDetailsActivity.class);
        showRecipeIntent.putExtra(Constants.RECIPE_NAME_KEY, recipeName);
        PendingIntent showRecipePendingIntent = PendingIntent.getActivity(context, 123, showRecipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_showrecipe_button, showRecipePendingIntent);
        views.setEmptyView(R.id.widget_ingredient_listview, R.id.widget_ingredients_emptyview);

        // set recipe name
        views.setTextViewText(R.id.widget_recipe_name, recipeName);

        // setup Intent for "Select Recipes" button
        Intent selectRecipeIntent = new Intent(context, WidgetConfigActivity.class);
        selectRecipeIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        selectRecipeIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
        PendingIntent selectRecipePendingIntent = PendingIntent.getActivity(context, 234, selectRecipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_selectrecipe_button, selectRecipePendingIntent);

        return views;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getBoolean(R.bool.isTablet)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setTitle(getString(R.string.widget_appbar_title));

        // if user backs out of activity don't create the widget
        setResult(RESULT_CANCELED);

        ActivityWidgetConfigBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_widget_config);
        WidgetActivityAdapter adapter = new WidgetActivityAdapter(this);
        binding.widgetConfigActivityRv.setAdapter(adapter);

        repository = ((SundayBakingApp) getApplication()).getRecipeRepository();
        repository.getRecipes().observe(this, adapter::setRecipes);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
    }

    private void saveRecipeSelection(String recipeName) {
        SharedPreferences sharedPref = getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.RECIPE_NAME_KEY, recipeName);
        editor.apply();
    }

    @Override
    public void onRecipeSelected(String recipeName) {
        RemoteViews views = setupWidget(this, recipeName, widgetId);
        saveRecipeSelection(recipeName);
        updateWidgetAndFinishActivity(views);
    }

    private void updateWidgetAndFinishActivity(RemoteViews views) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        appWidgetManager.updateAppWidget(widgetId, views);
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}
