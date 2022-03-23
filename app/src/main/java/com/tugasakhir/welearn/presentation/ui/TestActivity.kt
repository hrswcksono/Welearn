package com.tugasakhir.welearn.presentation.ui

import android.annotation.SuppressLint
import android.graphics.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.view.ViewConfiguration
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity(){

//    private lateinit var binding: ActivityTestBinding
    val myCanvasView = MyCanvasView(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityTestBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        val myCanvasView = MyCanvasView(this).apply {
            systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
            contentDescription = getString(R.string.canvasContentDescription)
        }

        setContentView(myCanvasView)
    }
}