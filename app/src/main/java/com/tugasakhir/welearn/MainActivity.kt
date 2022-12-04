package com.tugasakhir.welearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tugasakhir.welearn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        navView = binding.navView

        supportActionBar?.hide()

        val navController = findNavController(R.id.nav_host)

        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.home_nav,
            R.id.profile_nav
        ).build()

//        setupActionBarWithNavController(navController)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{ _, dest, _ ->
            when(dest.id) {
                R.id.home_nav -> navView.visibility = View.VISIBLE
                R.id.mode_angka_nav -> navView.visibility = View.VISIBLE
                R.id.mode_huruf_nav -> navView.visibility = View.VISIBLE
            }
        }
    }
}