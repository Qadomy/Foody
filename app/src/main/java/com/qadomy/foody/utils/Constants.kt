package com.qadomy.foody.utils

class Constants {
    companion object {
        const val BASE_URL = "https://api.spoonacular.com"
        const val API_KEY = "7763ffcd891c43b2b30efdd0d41850e5"


        // API Query Keys
        const val QUERY_SEARCH = "query"
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"


        // Room database
        const val RECIPES_DATABASE = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"


        // Bottom Sheet
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val DEFAULT_MEAL_TYPE = "Main Course"
        const val DEFAULT_DIET_TYPE = "Gluten Free"

        const val PREFERENCES_NAME = "foddy_preferences"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"

        const val PREFERENCES_BACK_ONLINE = "backOnline"


        // recipe bundle
        const val PARCELABLE_KEY = "recipeBundle"

    }
}