package com.qadomy.foody.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.qadomy.foody.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.qadomy.foody.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.qadomy.foody.utils.Constants.Companion.PREFERENCES_DIET_TYPE
import com.qadomy.foody.utils.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.qadomy.foody.utils.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.qadomy.foody.utils.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.qadomy.foody.utils.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedMealType = preferencesKey<String>(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = preferencesKey<Int>(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = preferencesKey<String>(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = preferencesKey<Int>(PREFERENCES_DIET_TYPE_ID)
    }


    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCES_NAME
    )

    /**  function for save data in data store */
    suspend fun saveMealAndDietType(mealType: String, mealId: Int, dietType: String, dietId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietId
        }
    }

    /**  variable for read data from data store */
    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0

            // pass with MealAndDietType class
            MealAndDietType(selectedMealType, selectedMealId, selectedDietType, selectedDietId)
        }


}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealId: Int,
    val selectedDietType: String,
    val selectedDietId: Int
)