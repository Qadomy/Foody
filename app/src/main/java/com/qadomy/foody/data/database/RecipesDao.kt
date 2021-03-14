package com.qadomy.foody.data.database

import androidx.room.*
import com.qadomy.foody.data.database.entities.FavoritesEntity
import com.qadomy.foody.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipesDao {


    @Query("SELECT * FROM RECIPES_TABLE ORDER BY id ASC")
    fun readRecipe(): Flow<List<RecipesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)


    /** insert recipe to favourite database and replace the duplicate recipe */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity)

    /** select all recipes from database */
    @Query("SELECT * FROM FAVORITE_RECIPES_TABLE ORDER BY id ASC")
    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>>

    /** delete recipe form favourite database */
    @Delete
    suspend fun deleteFavouriteRecipe(favoritesEntity: FavoritesEntity)

    /** delete all recipes from favourite database */
    @Query("DELETE FROM FAVORITE_RECIPES_TABLE")
    suspend fun deleteAllFavouriteRecipe()
}