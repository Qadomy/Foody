package com.qadomy.foody.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qadomy.foody.model.FoodRecipe

class RecipesTypeConverter {


    var gson = Gson()

    /**
     * this function we convert Food Recipes type to JSON string to store in database
     */
    @TypeConverter
    fun convertFoodRecipesToString(foodRecipe: FoodRecipe): String{
        return gson.toJson(foodRecipe)
    }

    /**
     * this function we convert String to Food Recipes to read it from database
     */
    @TypeConverter
    fun convertStringToFoodRecipe(data: String): FoodRecipe{
        val listType = object :TypeToken<FoodRecipe>(){}.type
        return gson.fromJson(data, listType)
    }
}