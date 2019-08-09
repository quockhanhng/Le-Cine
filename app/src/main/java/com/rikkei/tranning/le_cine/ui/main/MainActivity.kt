package com.rikkei.tranning.le_cine.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.support.v7.widget.Toolbar
import android.view.View
import com.rikkei.tranning.le_cine.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setToolBar()
        loadListingFragment(savedInstanceState)
    }

    private fun setToolBar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setTitle("Le Ciné")
            supportActionBar!!.setDisplayShowTitleEnabled(true)
        }
    }

    private fun loadListingFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.list_fragment, ListFragment(), ListFragment::class.simpleName)
                .commit()
        }
    }
}
