package com.tugasakhir.welearn.presentation.ui.score.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugasakhir.welearn.databinding.ActivityJoinedGameBinding
import com.tugasakhir.welearn.presentation.presenter.score.JoinedUserPresenter
import com.tugasakhir.welearn.presentation.ui.score.adapter.JoinedGameAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class JoinedGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinedGameBinding
    private val viewModel: JoinedUserPresenter by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinedGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackJoinedGame.setOnClickListener {
            onBackPressed()
        }
        supportActionBar?.hide()

        showList()
    }

    private fun showList(){
        val joinedGameAdapter = JoinedGameAdapter()

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.getJoinedGame()
                    .collectLatest {
                        joinedGameAdapter.setData(it)
                    }
            }
        }

        joinedGameAdapter.onItemClick = {
            val moveToScoreMulti = Intent(this@JoinedGameActivity, ScoreMultiplayerActivity::class.java)
            moveToScoreMulti.putExtra(ScoreMultiplayerActivity.ID_GAME, it.id)
            startActivity(moveToScoreMulti)
        }

        with(binding.rvJoinedGame) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = joinedGameAdapter
        }
    }
}