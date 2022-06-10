package com.example.mealfinder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MealViewModel: ViewModel() {

    private var mc: Int = 0
    private var r: Recipe? = null
    private var mealCount: MutableLiveData<Int> = MutableLiveData(mc)
    private var recipes: List<Hit> = listOf()
    private var search: String = ""
    private var recipeHolder: MutableLiveData<List<Hit>> = MutableLiveData<List<Hit>>(recipes)
    private var selectHolder: MutableLiveData<Recipe?> = MutableLiveData<Recipe?>(r)
    private var searchHolder: MutableLiveData<String> = MutableLiveData<String>(search)

    fun setSearch(searchValue: String) {
        search = searchValue
        searchHolder.value = search
    }

    fun getSearch(): String? {
        return searchHolder.value
    }

    fun incrementMealCount() {
        this.mc++
        mealCount.value = mc
    }

    fun decrementMealCount() {
        this.mc--
        mealCount.value = mc
    }

    fun getMealCount(): Int? {
        return mealCount.value
    }

    fun setRecipeHolder(results: List<Hit>?) {
        recipeHolder.value = results!!
    }

    fun getRecipeHolder(): List<Hit>? {
        return this.recipeHolder.value
    }

    fun getRecipeHolderSize(): Int {
        return this.recipes.size
    }

    fun setClickedRecipe(rcp: Recipe?) {
        this.r = rcp
        this.selectHolder = MutableLiveData<Recipe?>(this.r)
    }

    fun getClickedRecipe(): Recipe? {
        return this.selectHolder.value
    }


}