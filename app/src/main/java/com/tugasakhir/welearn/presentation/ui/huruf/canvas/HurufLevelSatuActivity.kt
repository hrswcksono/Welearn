package com.tugasakhir.welearn.presentation.ui.huruf.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.databinding.ActivityHurufLevelSatuBinding

class HurufLevelSatuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHurufLevelSatuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufLevelSatuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}