package com.example.mealfinder

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MainFragment: Fragment(R.layout.main_fragment) {

    private val URL: String =
        "https://api.edamam.com/api/recipes/"
    lateinit var activityRetriever: Context
    private val mvm: MealViewModel by activityViewModels()
    //private val checkBoxes: ArrayList<CheckBoxObject> =
        //arrayListOf(CheckBoxObject(breakfast, "&mealType=Breakfast"),
        //    CheckBoxObject(lunch, "&mealType=Lunch"), CheckBoxObject(dinner, "&mealType=Dinner"),
        //    CheckBoxObject(snack, "&mealType=Snack&mealType=Teatime"))
    private var urlBuilder: ArrayList<String> = arrayListOf()
    private var mealTypes: ArrayList<String> = arrayListOf()
    private var dietTypes: ArrayList<String> = arrayListOf()
    private var healthTypes: ArrayList<String> = arrayListOf()
    private var mealCount: Int = 0
    public var newURL = ""


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityRetriever = context
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val checkBoxes: ArrayList<CheckBoxObject> =
        arrayListOf(CheckBoxObject(breakfast, "&mealType=Breakfast"),
            CheckBoxObject(lunch, "&mealType=Lunch"), CheckBoxObject(dinner, "&mealType=Dinner"),
            CheckBoxObject(snack, "&mealType=Snack"), CheckBoxObject(highProtein, "&diet=high-protein"),
            CheckBoxObject(lowCarb, "&diet=low-carb"), CheckBoxObject(lowSodium, "&diet=low-sodium"),
            CheckBoxObject(lowFat, "&diet=low-fat"), CheckBoxObject(highFiber, "&diet=high-fiber"),
            CheckBoxObject(alcohol_free, "&health=alcohol-free"),
        CheckBoxObject(crustacean_free, "&health=crustacean-free"), CheckBoxObject(dairy_free, "&health=dairy-free"),
        CheckBoxObject(egg_free, "&health=egg-free"), CheckBoxObject(fish_free, "&health=fish-free"),
        CheckBoxObject(gluten_free, "&health=gluten-free"), CheckBoxObject(keto_friendly, "&health=keto-friendly"),
        CheckBoxObject(pork_free,"&health=pork-free"), CheckBoxObject(shellfish_free, "&health=shellfish-free"),
        CheckBoxObject(kosher, "&health=kosher"), CheckBoxObject(soy_free, "&health=soy-free"),
        CheckBoxObject(treenut_free, "&health=tree-nut-free"), CheckBoxObject(peanut_free, "&health=peanut-free"))

        val choices: ArrayList<CheckBoxObject> =
            arrayListOf(CheckBoxObject(breakfast, "Breakfast"),
                CheckBoxObject(lunch, "Lunch"), CheckBoxObject(dinner, "Dinner"),
                CheckBoxObject(snack, "Snack"), CheckBoxObject(highProtein, "high-protein"),
                CheckBoxObject(lowCarb, "low-carb"), CheckBoxObject(lowSodium, "low-sodium"),
                CheckBoxObject(lowFat, "low-fat"), CheckBoxObject(highFiber, "high-fiber"),
                CheckBoxObject(alcohol_free, "alcohol-free"),
                CheckBoxObject(crustacean_free, "crustacean-free"), CheckBoxObject(dairy_free, "dairy-free"),
                CheckBoxObject(egg_free, "egg-free"), CheckBoxObject(fish_free, "fish-free"),
                CheckBoxObject(gluten_free, "gluten-free"), CheckBoxObject(keto_friendly, "keto-friendly"),
                CheckBoxObject(pork_free,"pork-free"), CheckBoxObject(shellfish_free, "shellfish-free"),
                CheckBoxObject(kosher, "kosher"), CheckBoxObject(soy_free, "soy-free"),
                CheckBoxObject(treenut_free, "tree-nut-free"), CheckBoxObject(peanut_free, "peanut-free"))

        // Controls link building procedures based on which checkboxes are selected
        for (i in 0 until checkBoxes.size) {
            checkBoxes[i].cb.setOnClickListener {

                // If the checkbox chosen is one of the four meal types
                if (checkBoxes[i].linkText.equals("&mealType=Breakfast") || checkBoxes[i].linkText.equals("&mealType=Lunch") ||
                    checkBoxes[i].linkText.equals("&mealType=Dinner") || checkBoxes[i].linkText.equals("&mealType=Snack")) {

                        // ensures only one type of meal can be selected
                    if (mealCount < 1) {
                        urlBuilder.add(checkBoxes[i].linkText)
                        mealTypes.add(choices[i].linkText)
                        mealCount++
                    }

                    // Removes a selected meal type by pressing the selected meal a second time
                    else if (mealCount > 0) {
                        if (!checkBoxes[i].cb.isChecked) {
                            urlBuilder.remove(checkBoxes[i].linkText)
                            mealTypes.remove(choices[i].linkText)
                            mealCount--
                            Toast.makeText(
                                activityRetriever,
                                "Unselected",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        // Enforces the one meal rule
                        else {
                            checkBoxes[i].cb.isChecked = false
                            Toast.makeText(
                                activityRetriever,
                                "You can only select one type of meal",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    }
                }

                // Manages activities for checkboxes that are not mealtypes
                else {

                    // Deselecting a non mealtype checkbox
                    if (!checkBoxes[i].cb.isChecked) {
                        urlBuilder.remove(checkBoxes[i].linkText)
                        if (checkBoxes[i].linkText[1] == 'h') healthTypes.remove(choices[i].linkText)
                        if (checkBoxes[i].linkText[1] == 'd') dietTypes.remove(choices[i].linkText)
                        Toast.makeText(
                            activityRetriever,
                            "Unselected",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    // Selecting a non mealtype checkbox
                    else {
                        urlBuilder.add(checkBoxes[i].linkText)
                        if (checkBoxes[i].linkText[1] == 'h') healthTypes.add(choices[i].linkText)
                        if (checkBoxes[i].linkText[1] == 'd') dietTypes.add(choices[i].linkText)
                    }
                }
            }
        }

        beginSearch.setOnClickListener {
            beginSearch.text = "Searching..."
            for (i in 0 until urlBuilder.size) {
                newURL += urlBuilder[i]
            }
            if (foodQuery.text.toString().isEmpty()) {
                beginSearch.text = "Begin Search"
                Toast.makeText(activityRetriever, "Please type a search query", Toast.LENGTH_LONG).show()
            }
            else {
                newURL += "&q="
                mvm.setSearch(foodQuery.text.toString())
                for (i in 0 until foodQuery.text.toString().length) {
                    if (foodQuery.text.toString()[i] == ' ') {
                        newURL += "%20"
                    }
                    else {
                        newURL += foodQuery.text.toString()[i]
                    }
                }

                val retrofit: Retrofit =
                    Retrofit.Builder().baseUrl(URL).
                    addConverterFactory(GsonConverterFactory.create()).build()

                var mealFinderAPI = retrofit.create(MealFinderAPI::class.java)
                var call: Call<RecipeObject> = mealFinderAPI.getRecipeObject(mealTypes, dietTypes, healthTypes, foodQuery.text.toString())

                call.enqueue(object: Callback<RecipeObject> {
                    override fun onResponse(call: Call<RecipeObject>, response: Response<RecipeObject>) {
                        if (!response.isSuccessful()) {
                            return
                        }

                        var recipeObj: RecipeObject? = response.body()
                        mvm.setRecipeHolder(recipeObj?.hits)
                        if (recipeObj?.hits?.size!! > 0) {
                            view?.findNavController().navigate(R.id.resultsFragment)

                        }
                        else {
                            beginSearch.text = "Begin Search"
                            Toast.makeText(activityRetriever, "Your search returned no results.", Toast.LENGTH_LONG).show()
                        }
                    }
                    override fun onFailure(call: Call<RecipeObject>, t: Throwable) {
                        t.message?.let { Log.e("FAILED", it) }
                    }
                })

            }

        }

        apiseal.setImageResource(R.drawable.eda)

    }



}