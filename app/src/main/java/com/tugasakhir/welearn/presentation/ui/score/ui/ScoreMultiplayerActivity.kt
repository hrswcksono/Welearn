package com.tugasakhir.welearn.presentation.ui.score.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugasakhir.welearn.MainActivity
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.databinding.ActivityScoreMultiplayerBinding
import com.tugasakhir.welearn.presentation.presenter.score.ScoreMultiPresenter
import com.tugasakhir.welearn.presentation.ui.score.adapter.ScoreMultiAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScoreMultiplayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreMultiplayerBinding
    private val viewModel: ScoreMultiPresenter by viewModel()

    companion object{
        const val ID_GAME = "id_game"
        const val BACK = "back"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreMultiplayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        backButton()

        showList()
    }

    private fun backButton() {
        val back = intent.getStringExtra(BACK)
        if (back == "back") {
            binding.papanScoreBack.setImageResource(R.drawable.back_button)
            binding.papanScoreBack.setOnClickListener {
                onBackPressed()
            }
        } else {
            binding.papanScoreBack.setImageResource(R.drawable.ic_baseline_home_24)
            binding.papanScoreBack.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
                this.finish()
            }
        }

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
            setPadding(0, 10, 0, 0)
            setHasFixedSize(true)
            adapter = scoreMultiAdapter
        }
    }
}