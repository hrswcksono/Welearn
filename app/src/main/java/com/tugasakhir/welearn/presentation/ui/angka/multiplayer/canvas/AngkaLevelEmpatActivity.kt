package com.tugasakhir.welearn.presentation.ui.angka.multiplayer.canvas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import cn.pedant.SweetAlert.SweetAlertDialog
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.core.utils.CustomDialogBox
import com.tugasakhir.welearn.core.utils.Predict
import com.tugasakhir.welearn.core.utils.Template
import com.tugasakhir.welearn.core.utils.Template.encodeImage
import com.tugasakhir.welearn.core.utils.Template.speak
import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelEmpatBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.entity.SoalEntity
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import com.tugasakhir.welearn.presentation.presenter.score.SoalByIDPresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictAngkaPresenter
import com.tugasakhir.welearn.presentation.ui.score.ui.ScoreMultiplayerActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class AngkaLevelEmpatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAngkaLevelEmpatBinding
    private val soalViewModel: SoalByIDPresenter by viewModel()
    private val predictAngkaMultiPresenter: PredictAngkaMultiPresenter by viewModel()
    private val joinGamePresenter: JoinGamePresenter by viewModel()
    private val endGamePresenter: EndGamePresenter by viewModel()
    private val pushNotification: PushNotificationPresenter by viewModel()
    private val listUserParticipantPresenter: UserParticipantPresenter by viewModel()
    private var answer: String ?= null

    companion object {
        const val LEVEL_SOAL = "level_soal"
        const val ID_GAME = "id_game"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelEmpatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        main()

        binding.tvSelesaiA4.visibility = View.INVISIBLE

        supportActionBar?.hide()

        refreshCanvasOnClick()
        back()
        initializeCanvas()
    }

    private fun initializeCanvas() {
        binding.cnvsLevelEmpatAngkaOne.setStrokeWidth(30f)
        binding.cnvsLevelEmpatAngkaTwo.setStrokeWidth(30f)
    }

    private fun main() {
        val soalID = intent.getStringExtra(LEVEL_SOAL)
        val arrayID = soalID.toString().split("|")
        val idGame = intent.getStringExtra(ID_GAME)
        listDialog(idGame!!.toInt())
        joinGame(idGame!!.toInt())
        var index = 0
        var total = 0L
        val begin = Date().time
        var idSoal = arrayID[index]
        showScreen(idSoal)
        binding.submitEmpatAngka.setOnClickListener {
            hideButton()
            var score = 0
            val canvas1 = binding.cnvsLevelEmpatAngkaOne.getBitmap().scale(224, 224)
            val canvas2 = binding.cnvsLevelEmpatAngkaTwo.getBitmap().scale(224, 224)
            val result1 = Predict.PredictAngka(this, canvas1, answer?.get(0)!!)
            val result2 = Predict.PredictAngka(this, canvas2, answer?.get(1)!!)
            if (result1 + result2 == 20){
                score = 10
            }
            val end = Date().time
            total = (end - begin)/1000
            submitMulti(idGame.toInt(),idSoal.toInt(),total.toInt(), score)
            index++
            if (index < 3) {
                idSoal = arrayID[index]
                showScreen(idSoal)
//                    submitDrawing(idSoal)
            } else if (index == 3) {
                binding.progressBarA4.visibility = View.VISIBLE
                binding.tvSelesaiA4.visibility = View.VISIBLE
            }

        }
    }

    private fun submitMulti(idGame: Int, idSoal: Int,duration: Int, score: Int){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictAngkaMultiPresenter.predictAngkaMulti(idGame, idSoal, score,  duration)
                    .collectLatest {
                        endGame(idGame)
                    }
            }
        }
    }

    private fun showScreen(id: String) {
        binding.progressBarA4.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id.toInt()).collectLatest {
                    showData(it)
                    showButton()
                    answer = it.jawaban
                    binding.progressBarA4.visibility = View.INVISIBLE
                    refreshCanvas()
                }
            }
        }
    }

    private fun showData(data: SoalEntity){
        speak(data.keterangan + " " + data.soal)
        binding.spkEmpatAngka.setOnClickListener {
            speak(data.keterangan + " " + data.soal)
        }
        binding.soalAngkaDipilih.text = data.keterangan
        binding.levelAngkaKe.text = "Level ke ${data.idLevel}"
        binding.tvSoalAngkaEmpat.text = data.soal
    }

    private fun refreshCanvasOnClick(){
        binding.refreshEmpatAngka.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelEmpatAngkaOne.clearCanvas()
        binding.cnvsLevelEmpatAngkaTwo.clearCanvas()
    }

    private fun back(){
        binding.levelEmpatAngkaBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun joinGame(idGame: Int){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Default) {
                joinGamePresenter.joinGame(idGame.toString())
                    .collectLatest {  }
            }
        }
    }

    private fun endGame(idGame: Int){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                endGamePresenter.endGame(idGame.toString())
                    .collectLatest {
                        if (it == "Berhasil End Game"){
//                            Toast.makeText(this@HurufLevelNolActivity, "Pindah", Toast.LENGTH_SHORT).show()
                            showScoreMulti(idGame.toString())
                        }
                    }
            }
        }
    }

    private fun showScoreMulti(idGame: String) {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                pushNotification.pushNotification(
                    PushNotification(
                        NotificationData(
                            "Selesai"
                            ,"Pertandingan telah selesai"
                            ,"score",
                            "",
                            0,
                            idGame
                        ),
                        Constants.TOPIC_JOIN_ANGKA,
                        "high"
                    )
                ).collectLatest {
                    moveToScoreMulti(idGame)
                }
            }
        }
    }

    private fun moveToScoreMulti(idGame: String) {
        val moveToScoreMulti = Intent(this@AngkaLevelEmpatActivity, ScoreMultiplayerActivity::class.java)
        moveToScoreMulti.putExtra(ScoreMultiplayerActivity.ID_GAME, idGame)
        startActivity(moveToScoreMulti)
        this.finish()
    }

    private fun hideButton() {
        binding.submitEmpatAngka.visibility = View.GONE
        binding.refreshEmpatAngka.visibility = View.GONE
    }

    private fun showButton() {
        binding.submitEmpatAngka.visibility = View.VISIBLE
        binding.refreshEmpatAngka.visibility = View.VISIBLE
    }

    private fun listDialog(idGame: Int) {
        binding.btnUserParticipantA4.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    listUserParticipantPresenter.getListUserParticipant(idGame).collectLatest {
                        when(it) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                Template.listUser(it.data!!, this@AngkaLevelEmpatActivity)
                            }
                            is Resource.Error -> {}
                        }
                    }
                }
            }
        }
    }
}