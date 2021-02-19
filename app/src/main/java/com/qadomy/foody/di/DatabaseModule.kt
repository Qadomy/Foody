package com.qadomy.foody.di

import android.content.Context
import androidx.room.Room
import com.qadomy.foody.data.database.RecipesDatabase
import com.qadomy.foody.utils.Constants.Companion.RECIPES_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, RecipesDatabase::class.java, RECIPES_DATABASE
    ).build()


    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase) = database.recipesDao()

}