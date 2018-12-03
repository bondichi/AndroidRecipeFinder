package com.bondi.recipefinder.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bondi.recipefinder.R
import com.bondi.recipefinder.data.RecipeListAdapter
import com.bondi.recipefinder.model.Recipe
import kotlinx.android.synthetic.main.activity_recipe_list.*
import org.json.JSONException
import org.json.JSONObject

class  RecipeList : AppCompatActivity() {

    lateinit var volleyRequest: RequestQueue
    lateinit var recipeList: ArrayList<Recipe>
    var recipeAdapter: RecipeListAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        var url: String?

        var extras = intent.extras
        var ingredients = extras.get("ingredients")
        var searchTerm = extras.get("searchTerm")
        if (extras != null
            && !ingredients.equals("")
            && !searchTerm.equals("")) {
            // construct the url
            var tempUrl = "http://www.recipepuppy.com/api/?i=$ingredients&q=$searchTerm"
            url = tempUrl
        } else {
            url = "http://www.recipepuppy.com/api/?i=onions,garlic&q=omelet&p=3"
        }

        volleyRequest = Volley.newRequestQueue(this)
        recipeList = ArrayList()

        getRecipe(url)
    }

    fun getRecipe(url: String) {
        val recipeRequest = JsonObjectRequest(Request.Method.GET,
            url,
            Response.Listener { response: JSONObject ->
                try {
                    val resultArray = response.getJSONArray("results")

                    for (i in 1..resultArray.length()) {
                        var recipeObj = resultArray.getJSONObject(i)

                        var title = recipeObj.getString("title")
                        var link = recipeObj.getString("href")
                        var thumbnail = recipeObj.getString("thumbnail")
                        var ingredients = recipeObj.getString("ingredients")

//                        Log.d("Result===>>>", title)
                        var recipe = Recipe()
                        recipe.title = title
                        recipe.link = link
                        recipe.thumbnail = thumbnail
                        recipe.ingredients = ingredients

                        recipeList.add(recipe)

                        recipeAdapter = RecipeListAdapter(this, recipeList)
                        layoutManager = LinearLayoutManager(this)

                        recyclerViewId.adapter = recipeAdapter
                        recyclerViewId.layoutManager = layoutManager

                    }
                    recipeAdapter!!.notifyDataSetChanged()
                } catch(e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                error: VolleyError ->
                try {
                    Log.d("Error: ", error.toString())
                } catch(e: JSONException) {
                    e.printStackTrace()
                }
            })

        volleyRequest.add(recipeRequest)
    }
}
