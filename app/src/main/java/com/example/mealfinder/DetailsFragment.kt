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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.details_fragment.*
import com.squareup.picasso.Picasso
import java.util.*

class DetailsFragment: Fragment(R.layout.details_fragment) {
    private val mvm: MealViewModel by activityViewModels()
    lateinit var activityRetriever: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityRetriever = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recTitle.text = mvm.getClickedRecipe()?.label
        Picasso.get().load(mvm.getClickedRecipe()?.image).resize(2500,1500).into(recPicture)
        var ingrdnts = ""
        for (i in 0 until mvm.getClickedRecipe()?.ingredients!!.size) {
            ingrdnts += mvm.getClickedRecipe()?.ingredients?.get(i)?.text + "\n"
        }
        ingredients.text = ingrdnts
        edamamLogo.setImageResource(R.drawable.eda)
        webViewButton.setOnClickListener {
            view?.findNavController().navigate(R.id.recipeWebView)
        }
    }
}