package com.tugasakhir.welearn.presentation.ui.score.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityScoreHurufUserBinding
import com.tugasakhir.welearn.presentation.ui.score.viewmodel.UserScoreAngkaViewModel
import com.tugasakhir.welearn.presentation.ui.score.viewmodel.UserScoreHurufViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class ScoreHurufUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreHurufUserBinding
    private val viewModel: UserScoreHurufViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreHurufUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SharedPreference(this)
    }

    private fun show() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.userScoreHuruf(sessionManager.fetchAuthToken().toString()).collectLatest {

                }
            }
        }
    }
}