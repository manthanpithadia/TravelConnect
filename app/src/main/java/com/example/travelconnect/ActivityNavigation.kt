package com.example.travelconnect

import DBHelper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityNavigation : AppCompatActivity() {
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        /*dbHelper = DBHelper(this)

        dbHelper.clearAllLocations()
        // Example: Insert data
        val locationItem = LocationItem("1", "Location One", "Province A", "image_url", 4.5)
        dbHelper.insertLocation(locationItem)

        // Example: Query data
        val locations = dbHelper.queryLocations()
        for (location in locations) {
            Toast.makeText(
                this,
                "ID: ${location.id}, Name: ${location.name}, Province: ${location.province}, Rating: ${location.rating}",
                Toast.LENGTH_SHORT
            ).show()
        }*/

        // Find the NavHostFragment from the layout
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Find the BottomNavigationView
        bottomNavView = findViewById(R.id.bottom_navigation)

        // Set up the BottomNavigationView with the NavController
        bottomNavView.setupWithNavController(navController)
        // Inside a fragment, use findNavController() to navigate

    }
}