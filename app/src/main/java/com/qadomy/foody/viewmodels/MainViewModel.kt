package com.qadomy.foody.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.qadomy.foody.data.Repository
import com.qadomy.foody.data.database.entities.FavoritesEntity
import com.qadomy.foody.data.database.entities.RecipesEntity
import com.qadomy.foody.model.FoodRecipe
import com.qadomy.foody.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    /** ROOM DATABASE*/
    // region ROOM

    // asLiveData() extinction for convert [Flow] to live data
    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()
    val readFavouriteRecipes: LiveData<List<FavoritesEntity>> =
        repository.local.readFavouriteRecipes().asLiveData()

    /*
    function for insert data to local database
     */
    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }


    /*
    function for insert recipe in favourite database
     */
    private fun insertFavouriteRecipe(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteRecipe(favoritesEntity)
        }

    /*
   function for delete recipe from favourite database
    */
    private fun deleteFavouriteRecipe(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteRecipe(favoritesEntity)
        }

    /*
   function for delete all recipes from favourite database
    */
    private fun deleteAllFavouriteRecipe() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavouriteRecipes()
        }

    // endregion

    /** RETROFIT*/
    // region RETROFIT

    // MutableLiveData for store food recipes
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var searchRecipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    /*
    get recipes using scope from repository
     */
    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }
    // endregion


    /*
    check the call safety for get recipes
     */
    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)

                /**
                 * cache data in local database immediately after we recived it from API
                 */
                val foodRecipe = recipesResponse.value!!.data
                if (foodRecipe != null) {
                    // if data response not null [empty] we get data from offline database
                    offlineCacheRecipe(foodRecipe)
                }

            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes not found!")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection!!")
        }
    }

    /*
    check the call safety for search recipes
     */
    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchRecipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                searchRecipesResponse.value = handleFoodRecipesResponse(response)

            } catch (e: Exception) {
                searchRecipesResponse.value = NetworkResult.Error("Recipes not found!")
            }
        } else {
            searchRecipesResponse.value = NetworkResult.Error("No Internet Connection!!")
        }
    }

    /*
    function for save recipes into database
     */
    private fun offlineCacheRecipe(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }


    /*
    handel the response of food recipes
     */
    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found!")
            }
            response.isSuccessful -> {
                val foodRecipe = response.body()
                return NetworkResult.Success(foodRecipe!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }

        }
    }


    /*
      function for check the internet connection on phone and application
       */
    private fun hasInternetConnection(): Boolean {

        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false



        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

            else -> false
        }

    }


}