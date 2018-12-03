package com.bondi.recipefinder.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bondi.recipefinder.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButtonId.setOnClickListener {
            var intent = Intent(this, RecipeList::class.java)
            var ingredients = IngredientsEditId.text.toString().trim()
            var searchTerm = searchTermId.text.toString().trim()

            intent.putExtra("ingredients", ingredients)
            intent.putExtra("searchTerm", searchTerm)

            startActivity(intent)
        }
    }
}
