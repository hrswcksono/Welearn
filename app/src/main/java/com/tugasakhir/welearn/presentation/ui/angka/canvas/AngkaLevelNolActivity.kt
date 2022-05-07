package com.tugasakhir.welearn.presentation.ui.angka.canvas

import android.app.PendingIntent.getActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelNolBinding
import dev.abhishekkumar.canvasview.CanvasView

class AngkaLevelNolActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAngkaLevelNolBinding
//    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelNolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

//        navView = binding.navViewAngka
//
//        val navController = findNavController(R.id.nav_host_angka)
//
//        navView.setupWithNavController(navController)

//        binding.level1AngkaBack.setOnClickListener {
//            activity.onBackPressed()
//        }

        drawOne()
        drawTwo()
    }

    private fun drawOne(){
        val canvasView = findViewById<CanvasView>(R.id.canvasView)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }

    private fun drawTwo(){
        val canvasView = findViewById<CanvasView>(R.id.canvasView1)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
    }
}