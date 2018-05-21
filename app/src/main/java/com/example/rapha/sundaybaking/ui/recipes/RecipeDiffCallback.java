package com.example.rapha.sundaybaking.ui.recipes;

import android.support.v7.util.DiffUtil;

import com.example.rapha.sundaybaking.data.models.Recipe;

import java.util.List;

class RecipeDiffCallback extends DiffUtil.Callback {

    private final List<Recipe> oldRecipes;
    private final List<Recipe> newRecipes;

    public RecipeDiffCallback(List<Recipe> oldRecipes, List<Recipe> newRecipes) {
        this.oldRecipes = oldRecipes;
        this.newRecipes = newRecipes;
    }

    @Override
    public int getOldListSize() {
        return oldRecipes.size();
    }

    @Override
    public int getNewListSize() {
        return newRecipes.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRecipes.get(oldItemPosition).getName().equals(newRecipes.get(newItemPosition).getName());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRecipes.get(oldItemPosition).equals(newRecipes.get(newItemPosition));
    }
}
