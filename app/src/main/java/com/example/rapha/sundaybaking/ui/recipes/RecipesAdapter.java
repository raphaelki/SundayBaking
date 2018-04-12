package com.example.rapha.sundaybaking.ui.recipes;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.data.models.Recipe;
import com.example.rapha.sundaybaking.databinding.RcItemRecipeBinding;
import com.example.rapha.sundaybaking.util.RecipeDiffCallback;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RcItemRecipeBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.rc_item_recipe, parent, false);
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.binding.setRecipe(recipes.get(position));
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

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        private final RcItemRecipeBinding binding;

        public RecipeViewHolder(RcItemRecipeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}
