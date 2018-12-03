package com.bondi.recipefinder.data

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bondi.recipefinder.R
import com.bondi.recipefinder.ShowLinkActivity
import com.bondi.recipefinder.model.Recipe
import com.squareup.picasso.Picasso

class RecipeListAdapter(private val context: Context, private val recipeList: ArrayList<Recipe>) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindView(recipeList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.recipeTitle)
        var ingredients = itemView.findViewById<TextView>(R.id.Ingredients)
        var thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail)
        var linkButton = itemView.findViewById<Button>(R.id.linkButton)

        fun onBindView(recipe: Recipe) {
            title.text = recipe.title!!.trim()
            ingredients.text = recipe.ingredients

            if (!TextUtils.isEmpty(recipe.thumbnail)) {
                Picasso.get().load(recipe.thumbnail)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(thumbnail)
            } else {
                Picasso.get().load(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(thumbnail)
            }

            linkButton.setOnClickListener {
                var intent = Intent(context, ShowLinkActivity::class.java)
                intent.putExtra("link", recipe.link.toString())
                context.startActivity(intent)
            }

        }

    }
}