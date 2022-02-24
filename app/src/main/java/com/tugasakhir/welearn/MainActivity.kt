 package com.tugasakhir.welearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tugasakhir.welearn.databinding.ActivityMainBinding

 class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home,
            R.id.nav_profile,
            R.id.nav_quiz,
            R.id.nav_rank,
            R.id.nav_score
        ), binding.drawerLayout)

        setupActionBarWithNavController(findNavController(R.id.nav_host_fragment), appBarConfiguration)
        binding.navView.setupWithNavController(findNavController(R.id.nav_host_fragment))
    }

     override fun onSupportNavigateUp(): Boolean {
         return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration) ||
                 super.onSupportNavigateUp()
     }
}