package com.tugasakhir.welearn.presentation.ui.angka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ActivitySoalAngkaBinding

class SoalAngkaActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySoalAngkaBinding
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoalAngkaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navView = binding.navViewAngka

        val navController = findNavController(R.id.nav_host_angka)

        navView.setupWithNavController(navController)
    }
}