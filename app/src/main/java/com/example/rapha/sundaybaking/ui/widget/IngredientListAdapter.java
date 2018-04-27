package com.example.rapha.sundaybaking.ui.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.SundayBakingApp;
import com.example.rapha.sundaybaking.data.RecipeRepository;
import com.example.rapha.sundaybaking.data.models.Ingredient;

import java.util.List;

import timber.log.Timber;

public class IngredientListAdapter implements RemoteViewsService.RemoteViewsFactory {

    private List<Ingredient> ingredients;
    private RecipeRepository repository;
    private Context context;
    private String recipeName;

    public IngredientListAdapter(Context context, String recipeName) {
        Timber.d("Widget adapter created");
        this.context = context;
        this.recipeName = recipeName;
        repository = ((SundayBakingApp) context).getRecipeRepository();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Timber.d("Widget adapter onDataSetChanged");
        ingredients = repository.getIngredientsSync(recipeName);
    }

    @Override
    public void onDestroy() {
        repository = null;
        context = null;
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_item);
        Ingredient ingredient = ingredients.get(position);
        views.setTextViewText(R.id.widget_ingredientitem_quantity, ingredient.getQuantity() + " " + ingredient.getMeasure());
        views.setTextViewText(R.id.widget_ingredientitem_ingredient, ingredient.getIngredient());
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
