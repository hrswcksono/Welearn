package com.tugasakhir.welearn.presentation.ui.score.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityScoreMultiplayerBinding
import com.tugasakhir.welearn.presentation.presenter.score.ScoreMultiViewModel
import com.tugasakhir.welearn.presentation.ui.score.adapter.ScoreMultiAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class ScoreMultiplayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreMultiplayerBinding
    private val viewModel: ScoreMultiViewModel by viewModel()

    companion object{
        const val ID_GAME = "id_game"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreMultiplayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.papanScoreBack.setOnClickListener {
            onBackPressed()
        }

        showList()
    }

    private fun showList() {

        val idGame = intent.getStringExtra(ID_GAME)

        val scoreMultiAdapter = ScoreMultiAdapter()

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.scoreMulti(idGame!!.toInt())
                    .collectLatest {
                        scoreMultiAdapter.setData(it)
                    }
            }
        }

        with(binding.rvScoreMultiplayer) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = scoreMultiAdapter
        }
    }
}