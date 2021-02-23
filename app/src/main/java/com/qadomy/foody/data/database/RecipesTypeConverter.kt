package com.qadomy.foody.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qadomy.foody.model.FoodRecipe
import com.qadomy.foody.model.Result

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

    /**
     * this function we convert Food Result type to JSON string to store in database
     */
    @TypeConverter
    fun convertResultToString(result: Result):String{
        return gson.toJson(result)
    }

    /**
     * this function we convert String to Food Result to read it from database
     */
    @TypeConverter
    fun convertStringToResult(data:String):Result{
        val listType = object :TypeToken<Result>(){}.type
        return gson.fromJson(data, listType)
    }
}