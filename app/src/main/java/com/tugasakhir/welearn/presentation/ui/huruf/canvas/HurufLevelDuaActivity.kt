package com.tugasakhir.welearn.presentation.ui.huruf.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.databinding.ActivityHurufLevelDuaBinding

class HurufLevelDuaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHurufLevelDuaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufLevelDuaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}