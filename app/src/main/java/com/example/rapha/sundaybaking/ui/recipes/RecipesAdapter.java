package com.example.rapha.sundaybaking.ui.recipes;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.data.models.Recipe;
import com.example.rapha.sundaybaking.databinding.RvItemRecipeBinding;
import com.example.rapha.sundaybaking.ui.common.BindingViewHolder;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<BindingViewHolder<RvItemRecipeBinding>> {

    private List<Recipe> recipes;

    private RecipeClickCallback callback;

    public RecipesAdapter(@NonNull RecipeClickCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public BindingViewHolder<RvItemRecipeBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RvItemRecipeBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.rv_item_recipe, parent, false);
        return new BindingViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<RvItemRecipeBinding> holder, int position) {
        holder.binding.setRecipe(recipes.get(position));
        holder.binding.setCallback(callback);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }

    public void setRecipeList(final List<Recipe> newRecipes) {
        if (recipes == null) {
            recipes = newRecipes;
            notifyItemRangeChanged(0, newRecipes.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new RecipeDiffCallback(recipes, newRecipes));
            recipes = newRecipes;
            result.dispatchUpdatesTo(this);
        }
    }
}
