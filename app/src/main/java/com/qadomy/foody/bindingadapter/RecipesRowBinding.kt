package com.qadomy.foody.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.qadomy.foody.R

class RecipesRowBinding {

    companion object {


        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            /**
             * this function for convert the integer number of likes to string
             */
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinuets")
        @JvmStatic
        fun setNumberOfMinuets(textView: TextView, likes: Int) {
            /**
             * this function for convert the integer number of minuets to string
             */
            textView.text = likes.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, vegan: Boolean) {
            /**
             * this function for set green color to image and text of vegan icon
             */
            if (vegan) {
                when (view) {
                    is TextView -> view.setTextColor(
                        ContextCompat.getColor(
                            view.context,
                            R.color.green
                        )
                    )

                    is ImageView -> view.setColorFilter(
                        ContextCompat.getColor(
                            view.context,
                            R.color.green
                        )
                    )
                }
            }
        }


        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageURL: String) {
            /**
             * function fot load image from url, and set it in imageView
             */
            imageView.load(imageURL) {
                crossfade(600)
                // set error place holder icon when have error
                error(R.drawable.ic_image_not_supported)
            }
        }

    }
}