package com.bondi.recipefinder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_show_link.*

class ShowLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_link)

        var extras = intent.extras
        if (extras != null) {
            var link = extras.getString("link")

            webViewId.loadUrl(link.toString())
        }
    }
}
