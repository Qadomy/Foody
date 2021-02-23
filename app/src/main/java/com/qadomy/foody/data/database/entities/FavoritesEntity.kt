package com.qadomy.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.qadomy.foody.model.Result
import com.qadomy.foody.utils.Constants.Companion.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
) {
}