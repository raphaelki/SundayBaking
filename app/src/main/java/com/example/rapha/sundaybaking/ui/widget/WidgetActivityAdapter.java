package com.example.rapha.sundaybaking.ui.widget;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.data.models.Recipe;
import com.example.rapha.sundaybaking.databinding.WidgetConfigActivityItemBinding;
import com.example.rapha.sundaybaking.ui.BindingViewHolder;

import java.util.List;

public class WidgetActivityAdapter extends RecyclerView.Adapter<BindingViewHolder<WidgetConfigActivityItemBinding>> {

    private List<Recipe> recipes;
    private WidgetConfigItemCallback callback;

    public WidgetActivityAdapter(WidgetConfigItemCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public BindingViewHolder<WidgetConfigActivityItemBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WidgetConfigActivityItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.widget_config_activity_item, parent, false);
        return new BindingViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<WidgetConfigActivityItemBinding> holder, int position) {
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
