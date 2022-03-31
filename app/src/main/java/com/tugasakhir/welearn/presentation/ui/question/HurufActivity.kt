package com.tugasakhir.welearn.presentation.ui.question

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.databinding.ActivityHurufBinding

class HurufActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHurufBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHurufBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}