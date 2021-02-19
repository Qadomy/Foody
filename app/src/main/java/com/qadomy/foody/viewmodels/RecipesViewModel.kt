package com.qadomy.foody.viewmodels

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.qadomy.foody.data.DataStoreRepository
import com.qadomy.foody.utils.Constants
import com.qadomy.foody.utils.Constants.Companion.API_KEY
import com.qadomy.foody.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.qadomy.foody.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.qadomy.foody.utils.Constants.Companion.DEFAULT_RECIPES_NUMBER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RecipesViewModel @ViewModelInject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    // read data from data store
    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    /** function for read data from data store */
    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }

    // function fot set queries to fetch data from API
    fun applyQueries(): Map<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[Constants.QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = API_KEY
        queries[Constants.QUERY_TYPE] = mealType
        queries[Constants.QUERY_DIET] = dietType
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"


        return queries
    }

}