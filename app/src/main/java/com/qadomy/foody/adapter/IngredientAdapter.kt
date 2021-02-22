package com.qadomy.foody.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.qadomy.foody.R
import com.qadomy.foody.model.ExtendedIngredient
import com.qadomy.foody.utils.Constants.Companion.BASE_IMAGE_URL
import com.qadomy.foody.utils.RecipesDiffUtils
import kotlinx.android.synthetic.main.ingredients_row_layout.view.*

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.MyViewHolder>() {

    private var ingredientList = emptyList<ExtendedIngredient>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ingredients_row_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.ingredient_imageView.load(BASE_IMAGE_URL + ingredientList[position].image){
            crossfade(600)
            // set error place holder icon when have error
            error(R.drawable.ic_image_not_supported)
        }
        holder.itemView.ingredient_name.text = ingredientList[position].name.capitalize()
        holder.itemView.ingredient_amount.text = ingredientList[position].amount.toString()
        holder.itemView.ingredient_unit.text = ingredientList[position].unit
        holder.itemView.ingredient_consistency.text = ingredientList[position].consistency
        holder.itemView.ingredient_original.text = ingredientList[position].original
    }

    override fun getItemCount(): Int = ingredientList.size


    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil = RecipesDiffUtils(ingredientList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}