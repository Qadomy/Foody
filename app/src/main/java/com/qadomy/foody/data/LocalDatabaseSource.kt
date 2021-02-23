package com.qadomy.foody.data

import com.qadomy.foody.data.database.RecipesDao
import com.qadomy.foody.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDatabaseSource @Inject constructor(private val recipesDao: RecipesDao) {


    fun readDatabase(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipe()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }
}