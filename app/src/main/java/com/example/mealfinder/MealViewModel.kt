package com.example.mealfinder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MealViewModel: ViewModel() {

    private var mc: Int = 0
    private var r: Recipe? = null
    private var mealCount: MutableLiveData<Int> = MutableLiveData(mc)
    private var recipes: List<Hit> = listOf()
    private var recipeHolder: MutableLiveData<List<Hit>> = MutableLiveData<List<Hit>>(recipes)
    private var selectHolder: MutableLiveData<Recipe?> = MutableLiveData<Recipe?>(r)

    public fun incrementMealCount() {
        this.mc++
        mealCount.value = mc
    }

    public fun decrementMealCount() {
        this.mc--
        mealCount.value = mc
    }

    public fun getMealCount(): Int? {
        return mealCount.value
    }

    public fun setRecipeHolder(results: List<Hit>?) {
        recipeHolder.value = results
    }

    public fun getRecipeHolder(): List<Hit>? {
        return this.recipeHolder.value
    }

    public fun getRecipeHolderSize(): Int {
        return this.recipes.size
    }

    public fun setClickedRecipe(rcp: Recipe?) {
        this.r = rcp
        this.selectHolder = MutableLiveData<Recipe?>(this.r)
    }

    public fun getClickedRecipe(): Recipe? {
        return this.selectHolder.value
    }


}