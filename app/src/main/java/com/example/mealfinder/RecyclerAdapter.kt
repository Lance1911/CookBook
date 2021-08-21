package com.example.mealfinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recipe_item.view.*

class RecyclerAdapter(private val newList: ArrayList<RecipeItem>, private val listener: onRecipeClickListener):
    RecyclerView.Adapter<RecyclerAdapter.recyclerViewHolder>() {
    interface onRecipeClickListener {
        fun onRecipeClick(position: Int) {

        }
    }

    inner class recyclerViewHolder(cardView: View): RecyclerView.ViewHolder(cardView), View.OnClickListener {
        val image = cardView.imageView
        val recName = cardView.recipeName


        init {
            cardView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onRecipeClick(position)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.recyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return recyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.recyclerViewHolder, position: Int) {
        val currentItem = newList[position]
        holder.recName.text = currentItem.recipeName
        Picasso.get().load(currentItem.picture).resize(3000,1500).into(holder.image)
    }

    override fun getItemCount(): Int {
        return newList.size
    }


}