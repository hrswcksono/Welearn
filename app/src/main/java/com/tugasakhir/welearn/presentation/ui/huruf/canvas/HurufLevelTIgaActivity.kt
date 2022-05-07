package com.tugasakhir.welearn.presentation.ui.huruf.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.databinding.ActivityHurufLevelTigaBinding

class HurufLevelTIgaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHurufLevelTigaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufLevelTigaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}