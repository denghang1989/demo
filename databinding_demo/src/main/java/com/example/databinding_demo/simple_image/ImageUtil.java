package com.example.databinding_demo.simple_image;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @date 2017/6/12 11
 */
public class ImageUtil {

    @BindingAdapter({"imageLoader"})
    public static void imageLoader(ImageView view, String url) {
        Glide.with(view).load(url).into(view);
    }
}
