package com.example.rapha.sundaybaking.ui.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.rapha.sundaybaking.util.Constants;

import timber.log.Timber;

public class WidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Timber.d("Widget RemoteViewService onGetView");
        return new IngredientListAdapter(getApplicationContext(), intent.getStringExtra(Constants.RECIPE_NAME_KEY));
    }
}
