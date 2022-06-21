package com.example.musicgallery.view

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.musicgallery.R
import com.example.musicgallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_rock, R.id.navigation_classic, R.id.navigation_pop
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.swipeRefreshLayout.setOnRefreshListener {
            navController.currentDestination?.id?.let { dest ->
                navController.navigate(dest)
                    .also { binding.swipeRefreshLayout.isRefreshing = false }
            }
        }
    }

    override fun onBackPressed() {
        finishAndRemoveTask()
    }
}