package com.qadomy.foody.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.qadomy.foody.databinding.RecipesRowLayoutBinding
import com.qadomy.foody.model.FoodRecipe
import com.qadomy.foody.model.Result
import com.qadomy.foody.utils.RecipesDiffUtils

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.MyViewHolder>() {

    private var recipes = emptyList<Result>()

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings() //  updating view
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)

                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int = recipes.size


    fun setData(newData: FoodRecipe) {
        val recipesDiffUtils = RecipesDiffUtils(recipes, newData.results)
        val diffUtilsResult = DiffUtil.calculateDiff(recipesDiffUtils)

        recipes = newData.results

        diffUtilsResult.dispatchUpdatesTo(this)
    }
}