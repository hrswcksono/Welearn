package com.tugasakhir.welearn.presentation.ui.score.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.tugasakhir.welearn.MainActivity
import com.tugasakhir.welearn.databinding.ActivityScoreAngkaUserBinding
import com.tugasakhir.welearn.presentation.presenter.score.ScoreUserPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScoreAngkaUserActivity : AppCompatActivity() {

    companion object{
        const val LEVEL_SOAL = "level_soal"
    }

    private lateinit var binding: ActivityScoreAngkaUserBinding
    private val viewModel: ScoreUserPresenter by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreAngkaUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        show()

        binding.btnHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

//        binding.backDaftarSoalAngka.setOnClickListener {
////            startActivity(Intent(this, MainActivity::class.java))
//            onBackPressed()
//            onBackPressed()
//            onBackPressed()
//        }

    }

    private fun show() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.userScore(2).collectLatest {
                    binding.tvScoreUserAngka.text = it.score.toString()
                }
            }
        }
    }

    override fun onBackPressed() {
        return
    }
}