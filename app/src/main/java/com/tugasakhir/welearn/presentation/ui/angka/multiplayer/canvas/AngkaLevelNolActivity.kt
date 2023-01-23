package com.tugasakhir.welearn.presentation.ui.angka.multiplayer.canvas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import com.tugasakhir.welearn.core.utils.*
import com.tugasakhir.welearn.core.utils.Template.speak
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelNolBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.entity.SoalEntity
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import com.tugasakhir.welearn.presentation.presenter.soal.SoalByIDPresenter
import com.tugasakhir.welearn.presentation.ui.score.ui.ScoreMultiplayerActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class AngkaLevelNolActivity : AppCompatActivity() {
    companion object {
        const val LEVEL_SOAL = "level_soal"
        const val ID_GAME = "id_game"
    }

    private lateinit var binding: ActivityAngkaLevelNolBinding
    private val soalViewModel: SoalByIDPresenter by viewModel()
    private val predictAngkaMultiPresenter: PredictAngkaMultiPresenter by viewModel()
    private val joinGamePresenter: JoinGamePresenter by viewModel()
    private val endGamePresenter: EndGamePresenter by viewModel()
    private val pushNotification: PushNotificationPresenter by viewModel()
    private val listUserParticipantPresenter: UserParticipantPresenter by viewModel()
    private var answer: Char ?= null
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelNolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        sessionManager = SharedPreference(this)
        binding.tvSelesaiA0.visibility = View.INVISIBLE
        main()
        refreshCanvasOnClick()
        back()
        initializeCanvas()
    }

    private fun initializeCanvas() {
        binding.cnvsLevelNolAngka.setStrokeWidth(30f)
    }

    private fun main() {
        val soalID = intent.getStringExtra(LEVEL_SOAL)
        val arrayID = soalID.toString().split("|")
        val idGame = intent.getStringExtra(ID_GAME)
        listDialog(idGame!!.toInt())
        joinGame(idGame!!.toInt())
        var index = 0
        var total: Long
        val begin = Date().time
        var idSoal = arrayID[index]
        showScreen(idSoal)
        binding.submitNolAngka.setOnClickListener {
            hideButton()
            val canvas1 = binding.cnvsLevelNolAngka.getBitmap().scale(240,240)
            val result = Predict.PredictAngka(this, canvas1, answer!!)
            val end = Date().time
            total = (end - begin)/1000
            CustomDialogBox.dialogPredict(
                this@AngkaLevelNolActivity,
                {},
                result,
            )
            submitMulti(idGame.toInt(),idSoal.toInt(),total.toInt(), result)
            Template.saveMediaToStorage(canvas1, this, "${sessionManager.fetchName()}${idSoal}${Template.getDateTime()}")
            index++
            if (index < 3) {
                idSoal = arrayID[index]
                showScreen(idSoal)
//                    submitDrawing(idSoal)
            } else if (index == 3) {
                binding.progressBarA0.visibility = View.VISIBLE
                binding.tvSelesaiA0.visibility = View.VISIBLE
            }

        }
    }

    private fun showScreen(id: String) {
        binding.progressBarA0.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id.toInt()).collectLatest {
                    showData(it)
                    showButton()
                    answer = it.jawaban[0]
                    binding.progressBarA0.visibility = View.INVISIBLE
                    refreshCanvas()
                }
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

    private fun showData(data: SoalEntity){
        speak(data.keterangan + " " + data.soal)
        binding.spkNolAngka.setOnClickListener {
            speak(data.keterangan + " " + data.soal)
        }
        binding.soalAngkaDipilih.text = data.keterangan
        binding.levelAngkaKe.text = "Level ke ${data.idLevel}"
        binding.tvSoal.text = data.soal
    }

    private fun refreshCanvasOnClick(){
        binding.refreshNolAngka.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelNolAngka.clearCanvas()
    }

    private fun back(){
        binding.levelNolAngkaBack.setOnClickListener {
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
        val moveToScoreMulti = Intent(this@AngkaLevelNolActivity, ScoreMultiplayerActivity::class.java)
        moveToScoreMulti.putExtra(ScoreMultiplayerActivity.ID_GAME, idGame)
        startActivity(moveToScoreMulti)
        this.finish()
    }

    private fun hideButton() {
        binding.submitNolAngka.visibility = View.GONE
        binding.refreshNolAngka.visibility = View.GONE
    }

    private fun showButton() {
        binding.submitNolAngka.visibility = View.VISIBLE
        binding.refreshNolAngka.visibility = View.VISIBLE
    }

    private fun listDialog(idGame: Int) {
        binding.btnUserParticipantA0.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    listUserParticipantPresenter.getListUserParticipant(idGame).collectLatest {
                        Template.listUser(it, this@AngkaLevelNolActivity)
                    }
                }
            }
        }
    }
}