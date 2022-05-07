package com.tugasakhir.welearn.presentation.ui.angka.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelTigaBinding

class AngkaLevelTigaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAngkaLevelTigaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelTigaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}