package com.example.mealfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class RecipeWebView: Fragment(R.layout.recipe_web_view) {

    private val mvm: MealViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.recipe_web_view, container, false)
        var myWebView = view.findViewById<View>(R.id.webView) as WebView
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);

        myWebView.settings.javaScriptEnabled = true
        myWebView.webViewClient = WebViewClient()

        mvm.getClickedRecipe()?.let { myWebView.loadUrl(it.url) }

        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}