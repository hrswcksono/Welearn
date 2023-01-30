package com.tugasakhir.welearn.presentation.view.score.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityJoinedGameBinding
import com.tugasakhir.welearn.presentation.presenter.multiplayer.ScoreMultiPresenter
import com.tugasakhir.welearn.presentation.view.score.adapter.JoinedGameAdapter
import com.tugasakhir.welearn.utils.CustomDialogBox
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class JoinedGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinedGameBinding
    private val viewModel: ScoreMultiPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinedGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackJoinedGame.setOnClickListener {
            onBackPressed()
        }
        supportActionBar?.hide()

        sessionManager = this?.let { SharedPreference(it) }!!

        showList()
    }

    private fun showList(){
        val joinedGameAdapter = JoinedGameAdapter()

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.getJoinedGame(sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        when(it) {
                            is Resource.Success ->{
                                binding.pgJoinGame.visibility = View.GONE
                                if (it.data.toString() != "Data Kosong"){
                                    joinedGameAdapter.setData(it.data)
                                }else{
                                    CustomDialogBox.customDialog(this@JoinedGameActivity,"Data Kosong", "Belum Ada user submit pada ruangan ini")
                                }
                            }
                            is Resource.Loading ->{
                                binding.pgJoinGame.visibility = View.VISIBLE
                            }
                            is Resource.Error ->{
                                binding.pgJoinGame.visibility = View.GONE
                                CustomDialogBox.dialogNoInternet(this@JoinedGameActivity)
                            }
                        }
                    }
            }
        }

        joinedGameAdapter.onItemClick = {
            val moveToScoreMulti = Intent(this@JoinedGameActivity, ScoreMultiplayerActivity::class.java)
            moveToScoreMulti.putExtra(ScoreMultiplayerActivity.ID_GAME, it.id)
            moveToScoreMulti.putExtra(ScoreMultiplayerActivity.BACK, "back")
            startActivity(moveToScoreMulti)
        }

        with(binding.rvJoinedGame) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = joinedGameAdapter
        }
    }
}