package com.tugasakhir.welearn.presentation.view.angka.multiplayer.canvas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import com.tugasakhir.welearn.utils.*
import com.tugasakhir.welearn.utils.Template.speak
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelDuaBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.entity.Soal
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import com.tugasakhir.welearn.presentation.presenter.multiplayer.SoalByIDPresenter
import com.tugasakhir.welearn.presentation.view.score.ui.ScoreMultiplayerActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class AngkaLevelDuaActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_SOAL = "extra_soal"
        const val GAME_MODE = "game_mode"
        const val LEVEL_SOAL = "level_soal"
        const val ID_GAME = "id_game"
        const val NO_SOAL = "no_soal"
    }

    private lateinit var binding: ActivityAngkaLevelDuaBinding
    private val soalViewModel: SoalByIDPresenter by viewModel()
    private val predictAngkaMultiPresenter: PredictAngkaMultiPresenter by viewModel()
    private val joinGamePresenter: JoinGamePresenter by viewModel()
    private val gameAlreadyEndPresenter: GameAlreadyEndPresenter by viewModel()
    private val pushNotification: PushNotificationPresenter by viewModel()
    private val listUserParticipantPresenter: UserParticipantPresenter by viewModel()
    private var answer: Char ?= null
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelDuaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        sessionManager = SharedPreference(this)
        main()
        binding.tvSelesaiA2.visibility = View.INVISIBLE

        refreshCanvasOnClick()
        back()
        initializeCanvas()
    }

    private fun initializeCanvas() {
        binding.cnvsLevelDuaAngka.setStrokeWidth(30f)
    }

    private fun main() {
        val soalID = intent.getStringExtra(LEVEL_SOAL)
        val arrayID = soalID.toString().split("|")
        val idGame = intent.getStringExtra(ID_GAME)
        listDialog(idGame!!.toInt())
        var index = 0
        var total = 0L
        val begin = Date().time
        var idSoal = arrayID[index]
        showScreen(idSoal)
        binding.submitDuaAngka.setOnClickListener {
            hideButton()
            val bitmap = binding.cnvsLevelDuaAngka.getBitmap().scale(224, 224)
            val result = Predict.PredictAngka(this, bitmap, answer!!)
            val end = Date().time
            total = (end - begin)/1000
            CustomDialogBox.dialogPredict(
                this@AngkaLevelDuaActivity,
                {},
                result,
            )
            submitMulti(idGame.toInt(),idSoal.toInt(),total.toInt(), result)
            index++
            if (index < 3) {
                idSoal = arrayID[index]
                showScreen(idSoal)
            }else if (index == 3){
                binding.progressBarA2.visibility = View.VISIBLE
                binding.tvSelesaiA2.visibility = View.VISIBLE
            }

        }
    }

    private fun hideButton() {
        binding.submitDuaAngka.visibility = View.GONE
        binding.refreshDuaAngka.visibility = View.GONE
    }

    private fun showButton() {
        binding.submitDuaAngka.visibility = View.VISIBLE
        binding.refreshDuaAngka.visibility = View.VISIBLE
    }

    private fun submitMulti(idGame: Int, idSoal: Int,duration: Int, score: Int){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictAngkaMultiPresenter.predictAngkaMulti(idGame, idSoal, score,  duration, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        endGame(idGame)
                    }
            }
        }
    }

    private fun showScreen(id: String) {
        binding.progressBarA2.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id.toInt(), sessionManager.fetchAuthToken()!!).collectLatest {
                    showData(it)
                    showButton()
                    answer = it.jawaban[0]
                    binding.progressBarA2.visibility = View.INVISIBLE
                    refreshCanvas()
                }
            }
        }
    }

    private fun showData(data: Soal){
        speak(data.keterangan + " " + data.soal)
        binding.spkDuaAngka.setOnClickListener {
            speak(data.keterangan + " " + data.soal)
        }
        binding.soalAngkaDipilih.text = data.keterangan
        binding.levelAngkaKe.text = "Level ke ${data.idLevel}"
        binding.tvSoalAngkaDua.text = data.soal
    }

    private fun refreshCanvasOnClick(){
        binding.refreshDuaAngka.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelDuaAngka.clearCanvas()
    }

    private fun back() {
        binding.levelDuaAngkaBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun endGame(idGame: Int){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                gameAlreadyEndPresenter.gameAlreadyEnd(idGame.toString(), sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        if (it == "Room Berhasil Ditutup"){
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
                            idGame,
                        ),
                        Template.getTopic(sessionManager.fetchIDRoom().toString()),
                        "high"
                    )
                ).collectLatest {
                }
            }
        }
    }

    private fun listDialog(idGame: Int) {
        binding.btnUserParticipantA2.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    listUserParticipantPresenter.getListUserParticipant(idGame, sessionManager.fetchAuthToken()!!).collectLatest {
                        Template.listUser(it, this@AngkaLevelDuaActivity)
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
    }
}