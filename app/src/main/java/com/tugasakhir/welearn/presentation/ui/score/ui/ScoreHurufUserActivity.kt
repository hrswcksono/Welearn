package com.tugasakhir.welearn.presentation.ui.score.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ActivityScoreHurufUserBinding
import com.tugasakhir.welearn.domain.entity.ScoreEntity
import com.tugasakhir.welearn.presentation.presenter.score.ScoreUserPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScoreHurufUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreHurufUserBinding
    private val viewModel: ScoreUserPresenter by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreHurufUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        show()

        binding.btnBackDaftarSoalHuruf.setOnClickListener {
            backListSoal()
        }
    }

    private fun show() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.userScore(1).collectLatest {
                    showData(it)
                }
            }
        }
    }

    private fun showData(data: ScoreEntity) {
        binding.tvScoreUserHuruf.text = data.score.toString()
    }

    override fun onBackPressed() {
        return
    }

    private fun backListSoal() {
        val navController = findNavController(R.id.nav_host)
//        navController.navigateUp()
        navController.navigate(R.id.list_soal_angka_nav)
    }

}