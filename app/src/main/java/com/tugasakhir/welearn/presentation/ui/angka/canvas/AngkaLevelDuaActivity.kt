package com.tugasakhir.welearn.presentation.ui.angka.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelDuaBinding

class AngkaLevelDuaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAngkaLevelDuaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelDuaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}