package com.example.rapha.sundaybaking.ui;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.rapha.sundaybaking.util.GlideApp;

public class BindingsAdapters {
    @BindingAdapter(value = {"imageUrl", "placeholder"})
    public static void loadImage(ImageView view, String imageUrl, Drawable placeholder) {
        GlideApp.with(view.getContext())
                .load(imageUrl)
                .placeholder(placeholder)
                .into(view);
    }
}
