package com.qadomy.foody.data

import com.qadomy.foody.data.database.RecipesDao
import com.qadomy.foody.data.database.entities.FavoritesEntity
import com.qadomy.foody.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDatabaseSource @Inject constructor(private val recipesDao: RecipesDao) {


    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipe()
    }


    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }


    fun readFavouriteRecipes(): Flow<List<FavoritesEntity>> {
        return recipesDao.readFavoriteRecipes()
    }

    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity){
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }

    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity){
        recipesDao.deleteFavouriteRecipe(favoritesEntity)
    }

    suspend fun deleteAllFavouriteRecipes(){
        recipesDao.deleteAllFavouriteRecipe()
    }
}