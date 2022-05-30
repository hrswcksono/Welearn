package com.tugasakhir.welearn.presentation.ui.huruf.multiplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.databinding.ActivityHurufReadyBinding
import com.tugasakhir.welearn.presentation.ui.PushNotificationViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class HurufReadyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHurufReadyBinding
    private val viewModel: PushNotificationViewModel by viewModel()
    private val viewModelSoal: SoalHurufByIDViewModel by viewModel()

    companion object{
        const val LEVEL_HURUF = "level_huruf"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufReadyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnHurufReady.setOnClickListener {

        }
    }

    override fun onBackPressed() {
        return
    }

}