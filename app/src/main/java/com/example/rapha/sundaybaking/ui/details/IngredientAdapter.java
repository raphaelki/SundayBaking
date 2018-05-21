package com.example.rapha.sundaybaking.ui.details;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.data.models.Ingredient;
import com.example.rapha.sundaybaking.databinding.RvItemIngredientBinding;
import com.example.rapha.sundaybaking.ui.common.BindingViewHolder;

import java.util.List;

class IngredientAdapter extends RecyclerView.Adapter<BindingViewHolder<RvItemIngredientBinding>> {

    private List<Ingredient> ingredients;

    @NonNull
    @Override
    public BindingViewHolder<RvItemIngredientBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvItemIngredientBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.rv_item_ingredient, parent,
                false);
        return new BindingViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<RvItemIngredientBinding> holder, int position) {
        holder.binding.setIngredient(ingredients.get(position));
        holder.binding.executePendingBindings();
    }

    public void setIngredientList(List<Ingredient> newIngredients) {
//        if (ingredients == null){
//            ingredients = newIngredients;
//            notifyItemRangeChanged(0,newIngredients.size());
//        }
        ingredients = newIngredients;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ingredients == null ? 0 : ingredients.size();
    }
}
