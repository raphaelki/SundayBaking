package com.example.rapha.sundaybaking.ui.widget;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.data.models.Recipe;
import com.example.rapha.sundaybaking.databinding.RvItemWidgetRecipeConfigBinding;
import com.example.rapha.sundaybaking.ui.common.BindingViewHolder;
import com.example.rapha.sundaybaking.ui.recipes.RecipeClickCallback;

import java.util.List;

public class WidgetActivityAdapter extends RecyclerView.Adapter<BindingViewHolder<RvItemWidgetRecipeConfigBinding>> {

    private List<Recipe> recipes;
    private RecipeClickCallback callback;

    public WidgetActivityAdapter(RecipeClickCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public BindingViewHolder<RvItemWidgetRecipeConfigBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvItemWidgetRecipeConfigBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_widget_recipe_config, parent, false);
        return new BindingViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<RvItemWidgetRecipeConfigBinding> holder, int position) {
        holder.binding.setRecipe(recipes.get(position));
        holder.binding.setCallback(callback);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }
}
