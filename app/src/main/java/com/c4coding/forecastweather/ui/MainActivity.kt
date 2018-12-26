package com.c4coding.forecastweather.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.c4coding.forecastweather.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

const val LOCATION_REQUEST_CODE =1

class MainActivity : AppCompatActivity() ,KodeinAware{
    override val kodein by closestKodein()
    private val mFusedLocationProviderClient :FusedLocationProviderClient by instance()

    private val mLocationCallback =object  :LocationCallback(){
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
        }
    }

    lateinit var mNavController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mNavController=Navigation.findNavController(this,R.id.nav_host_fragment)

        bottom_navbar.setupWithNavController(mNavController)

        NavigationUI.setupActionBarWithNavController(this,mNavController)

        requestLocationPermission()

        if (hasLocationPermission()){
            bindLocationManager()
        }else
            requestLocationPermission()
    }
    private fun bindLocationManager(){
        LifecycleBoundLocationmanager(this,mFusedLocationProviderClient,mLocationCallback)
    }
    private fun hasLocationPermission() = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            LOCATION_REQUEST_CODE
        )
    }
    override fun onSupportNavigateUp() =NavigationUI.navigateUp(mNavController,null)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode== LOCATION_REQUEST_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                bindLocationManager()
            }else{
                Toast.makeText(this,"Please,set Location manually in settings ",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
