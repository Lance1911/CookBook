package com.example.mealfinder

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*
import androidx.fragment.app.activityViewModels


interface MealFinderAPI {
    @GET("v2?type=public&app_id=d2f411c2&app_key=7b9fd43b24d3c9bc8a546ef7eb8c3c48")
    fun getRecipeObject(@Query("mealType") type: ArrayList<String>,
                        @Query("diet") dietType: ArrayList<String>,
                        @Query("health") healthType: ArrayList<String>,
                        @Query("q") search: String): Call<RecipeObject>
}