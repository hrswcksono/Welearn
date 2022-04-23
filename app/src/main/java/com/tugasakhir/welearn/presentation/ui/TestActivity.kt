package com.tugasakhir.welearn.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ActivityTestBinding
import dev.abhishekkumar.canvasview.CanvasView

class TestActivity : AppCompatActivity(){

    private lateinit var binding: ActivityTestBinding
//    val myCanvasView = MyCanvasView(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val myCanvasView = MyCanvasView(this).apply {
//            systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
//            contentDescription = getString(R.string.canvasContentDescription)
//        }

//        setContentView(myCanvasView)

        val canvasView = findViewById<CanvasView>(R.id.canvasView)
        canvasView.setColorBackground(R.color.purple_200)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)

//        val canvasView = CanvasView(this)
//        binding.parentView.addView(canvasView)


    }
}