package com.tugasakhir.welearn.presentation.ui.angka.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelSatuBinding

class AngkaLevelSatuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAngkaLevelSatuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelSatuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}