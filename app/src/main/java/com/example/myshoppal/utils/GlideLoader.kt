package com.example.myshoppal.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.IOException

class GlideLoader(val context: Context) {

    /**
     * A function to load image from Uri or URL for the user profile picture.
     */

    /**
     * A function to load image from Uri or URL for the product image.
     */
    fun loadProductPicture(image: Any, imageView: ImageView) {
        try {
            Glide
                .with(context)
                .load(image) // Uri or URL of the image
                .centerCrop() // Scale type of the image.
                .into(imageView) // the view in which the image will be loaded.
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}