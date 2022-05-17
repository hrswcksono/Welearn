package com.tugasakhir.welearn

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.core.utils.FirebaseService
import com.tugasakhir.welearn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/myTopics")
        FirebaseService.token = "/topics/myTopics"

        navView = binding.navView

        supportActionBar?.hide()

        val navController = findNavController(R.id.nav_host)

        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.home_nav,
            R.id.profile_nav
        ).build()

//        setupActionBarWithNavController(navController)
        navView.setupWithNavController(navController)
    }
}