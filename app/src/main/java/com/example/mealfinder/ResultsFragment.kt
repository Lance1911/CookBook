package com.example.mealfinder

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import android.view.View
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.results_fragment.*
import kotlinx.android.synthetic.main.details_fragment.*
import java.util.*

class ResultsFragment: Fragment(R.layout.results_fragment), RecyclerAdapter.onRecipeClickListener {
    private val mvm: MealViewModel by activityViewModels()
    lateinit var activityRetriever: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityRetriever = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var writtenList: String? = ""
        var recyclerList: ArrayList<RecipeItem> = arrayListOf()
        var size: Int = mvm.getRecipeHolder()?.size!!
        for (i in 0 until size) {
            writtenList += mvm.getRecipeHolder()?.get(i)?.recipe?.label + "\n"
            recyclerList.add(RecipeItem(mvm.getRecipeHolder()?.get(i)?.recipe?.image!!,
                mvm.getRecipeHolder()?.get(i)?.recipe?.label!!))
        }

        SearchResults.text = "Search Results for \"" + mvm.getSearch() + "\""
        recyclerView.adapter = RecyclerAdapter(recyclerList, this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

    }

    override fun onRecipeClick(position: Int) {
//        Toast.makeText(activityRetriever,
//            mvm.getRecipeHolder()?.get(position)?.recipe?.label, Toast.LENGTH_LONG).show()
        mvm.setClickedRecipe(mvm.getRecipeHolder()?.get(position)?.recipe)
        view?.findNavController()?.navigate(R.id.detailsFragment)

    }

}