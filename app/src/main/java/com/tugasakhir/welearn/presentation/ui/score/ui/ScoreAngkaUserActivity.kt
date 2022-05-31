package com.tugasakhir.welearn.presentation.ui.score.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityScoreAngkaUserBinding
import com.tugasakhir.welearn.presentation.ui.score.viewmodel.ScoreHurufViewModel
import com.tugasakhir.welearn.presentation.ui.score.viewmodel.UserScoreAngkaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class ScoreAngkaUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreAngkaUserBinding
    private val viewModel: UserScoreAngkaViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreAngkaUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SharedPreference(this)

        show()

    }

    private fun show() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.userScoreAngka(sessionManager.fetchAuthToken().toString()).collectLatest {

                }
            }
        }
    }
}