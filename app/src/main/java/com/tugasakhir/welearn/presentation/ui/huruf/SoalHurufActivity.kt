package com.tugasakhir.welearn.presentation.ui.huruf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.databinding.ActivitySoalHurufBinding

class SoalHurufActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySoalHurufBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoalHurufBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}