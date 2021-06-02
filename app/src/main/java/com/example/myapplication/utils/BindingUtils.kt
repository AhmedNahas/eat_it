package com.example.myapplication.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.myapplication.R

class BindingUtils {

    companion object {
        @JvmStatic
        @BindingAdapter("intText")
        fun setText(view: TextView, value: Long) {
            view.text = value.toString()
        }

        @JvmStatic
        @BindingAdapter("imageFood")
        fun setImageUrl(imageView: ImageView, url: String?) {
            val context = imageView.context
            if (url!!.isNotEmpty() && url != "null") {
                Glide.with(context).load(url)
                    .placeholder(R.color.black).into(imageView)

            }
        }
    }

}