package com.tugasakhir.welearn.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tugasakhir.welearn.R
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
                R.id.angka_level_nol_nav -> navView.visibility = View.GONE
                R.id.angka_level_satu_nav -> navView.visibility = View.GONE
                R.id.angka_level_dua_nav -> navView.visibility = View.GONE
                R.id.angka_level_tiga_nav -> navView.visibility = View.GONE
                R.id.angka_level_empat_nav -> navView.visibility = View.GONE
                R.id.huruf_level_nol_nav -> navView.visibility = View.GONE
                R.id.huruf_level_satu_nav -> navView.visibility = View.GONE
                R.id.huruf_level_dua_nav -> navView.visibility = View.GONE
                R.id.huruf_level_tiga_nav -> navView.visibility = View.GONE
                R.id.match_angka_nav -> navView.visibility = View.GONE
                R.id.match_huruf_nav -> navView.visibility = View.GONE
                R.id.angka_ready_nav -> navView.visibility = View.GONE
                R.id.huruf_ready_nav -> navView.visibility = View.GONE
                R.id.score_angka_user_nav -> navView.visibility = View.GONE
                R.id.score_huruf_user_nav -> navView.visibility = View.GONE
                R.id.multi_angka_nav -> navView.visibility = View.VISIBLE
                R.id.multi_huruf_nav -> navView.visibility = View.VISIBLE
            }
        }
    }

}