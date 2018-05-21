package com.example.rapha.sundaybaking.ui.details;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.data.models.InstructionStep;
import com.example.rapha.sundaybaking.databinding.RvItemStepBinding;
import com.example.rapha.sundaybaking.ui.common.BindingViewHolder;

import java.util.List;

class InstructionStepAdapter extends RecyclerView.Adapter<BindingViewHolder<RvItemStepBinding>> {

    private List<InstructionStep> steps;

    private final InstructionStepClickCallback callback;

    public InstructionStepAdapter(InstructionStepClickCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public BindingViewHolder<RvItemStepBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvItemStepBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.rv_item_step,
                parent,
                false);
        return new BindingViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<RvItemStepBinding> holder, int position) {
        holder.binding.setStep(steps.get(position));
        holder.binding.setCallback(callback);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return steps == null ? 0 : steps.size();
    }

    public void setStepList(List<InstructionStep> newSteps) {
        steps = newSteps;
        notifyDataSetChanged();
    }

}
