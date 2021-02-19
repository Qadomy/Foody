package com.qadomy.foody.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.qadomy.foody.model.FoodRecipe
import com.qadomy.foody.utils.Constants.Companion.RECIPES_TABLE


@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(var foodRecipe: FoodRecipe) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}