package com.qadomy.foody.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.qadomy.foody.data.database.entities.RecipesEntity
import com.qadomy.foody.model.FoodRecipe
import com.qadomy.foody.utils.NetworkResult

class RecipesBinding {
    companion object {


        @BindingAdapter("imageReadApiResponse", "imageReadDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ) {
            when {
                apiResponse is NetworkResult.Error && database.isNullOrEmpty() -> {
                    imageView.visibility = View.VISIBLE
                }
                apiResponse is NetworkResult.Loading -> {
                    imageView.visibility = View.INVISIBLE
                }
                apiResponse is NetworkResult.Success -> {
                    imageView.visibility = View.INVISIBLE
                }
            }
        }


        @BindingAdapter("textReadApiResponse", "textReadDatabase", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(
            textView: TextView,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ) {

            when {
                apiResponse is NetworkResult.Error && database.isNullOrEmpty() -> {
                    textView.visibility = View.VISIBLE
                    textView.text = apiResponse.message.toString()
                }
                apiResponse is NetworkResult.Loading -> {
                    textView.visibility = View.INVISIBLE
                }
                apiResponse is NetworkResult.Success -> {
                    textView.visibility = View.INVISIBLE
                }
            }

        }
    }
}














