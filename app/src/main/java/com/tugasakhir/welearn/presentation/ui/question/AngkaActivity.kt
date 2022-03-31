package com.tugasakhir.welearn.presentation.ui.question

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.databinding.ActivityAngkaBinding

class AngkaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAngkaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAngkaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}