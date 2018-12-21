package com.c4coding.forecastweather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.c4coding.forecastweather.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mNavController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mNavController=Navigation.findNavController(this,R.id.nav_host_fragment)

        bottom_navbar.setupWithNavController(mNavController)

        NavigationUI.setupActionBarWithNavController(this,mNavController)

    }

    override fun onSupportNavigateUp() =NavigationUI.navigateUp(mNavController,null)
}
