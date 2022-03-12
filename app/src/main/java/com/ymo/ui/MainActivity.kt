package com.ymo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ymo.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val appbarConfiguration = AppBarConfiguration.Builder()
        .setFallbackOnNavigateUpListener {
            finish()
            super.onSupportNavigateUp()
        }
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.upcomingFragment, R.id.popularFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun navView(isVisible: Boolean) {
        bottom_nav.isVisible = isVisible
    }

    private fun setUpToolbar() {
        setupActionBarWithNavController(
            findNavController(R.id.nav_host_fragment),
            appbarConfiguration
        )
    }
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appbarConfiguration)
    }
}