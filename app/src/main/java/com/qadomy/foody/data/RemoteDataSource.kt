package com.qadomy.foody.data

import com.qadomy.foody.data.network.FoodRecipesAPI
import com.qadomy.foody.model.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val foodRecipesAPI: FoodRecipesAPI) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesAPI.getRecipes(queries)
    }

}