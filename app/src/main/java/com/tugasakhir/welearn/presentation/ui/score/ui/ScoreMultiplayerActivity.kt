package com.tugasakhir.welearn.presentation.ui.score.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.databinding.ActivityScoreMultiplayerBinding

class ScoreMultiplayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreMultiplayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreMultiplayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}