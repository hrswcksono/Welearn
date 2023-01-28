package com.tugasakhir.welearn.presentation.view.huruf.multiplayer.canvas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import com.tugasakhir.welearn.utils.*
import com.tugasakhir.welearn.utils.Template.speak
import com.tugasakhir.welearn.databinding.ActivityHurufLevelNolBinding
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

class HurufLevelNolActivity : AppCompatActivity() {

    companion object {
        const val LEVEL_SOAL = "level_soal"
        const val ID_GAME = "id_game"
    }

    private lateinit var binding: ActivityHurufLevelNolBinding
    private val soalViewModel: SoalByIDPresenter by viewModel()
    private val predictHurufMultiPresenter: PredictHurufMultiPresenter by viewModel()
    private val joinGamePresenter: JoinGamePresenter by viewModel()
    private val gameAlreadyEndPresenter: GameAlreadyEndPresenter by viewModel()
    private val pushNotification: PushNotificationPresenter by viewModel()
    private val listUserParticipantPresenter: UserParticipantPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference
    private var answer: Char ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufLevelNolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.tvSelesaiH0.visibility = View.INVISIBLE

        sessionManager = SharedPreference(this)

        main()
        refreshCanvasOnClick()
        back()
        initializeCanvas()

    }

    private fun initializeCanvas(){
        binding.cnvsLevelNolHuruf.setStrokeWidth(30f)
    }

    private fun main() {
        val soalID = intent.getStringExtra(LEVEL_SOAL)
        val arrayID = soalID.toString().split("|")
        val idGame = intent.getStringExtra(ID_GAME)
        listDialog(idGame!!.toInt())
        var index = 0
        var total = 0L
        val begin = Date().time
//            Toast.makeText(this, idGame.toString(), Toast.LENGTH_SHORT).show()
        var idSoal = arrayID[index]
//            Toast.makeText(this, idSoal, Toast.LENGTH_SHORT).show()
        showScreen(idSoal)
        binding.submitNolHuruf.setOnClickListener {
            hideButton()
            val bitmap = binding.cnvsLevelNolHuruf.getBitmap().scale(224, 224)
            val result = Predict.predictHuruf(this, bitmap, answer!!)
            val end = Date().time
            total = (end - begin)/1000
            CustomDialogBox.dialogPredict(
                this@HurufLevelNolActivity,
                {},
                result,
            )
            submitMulti(idGame.toInt(),idSoal.toInt(),total.toInt(), result)
            index++
            if (index < 3) {
                idSoal = arrayID[index]
                showScreen(idSoal)
//                    submitDrawing(idSoal)
            } else if (index == 3) {
                binding.progressBarH0.visibility = View.VISIBLE
                binding.tvSelesaiH0.visibility = View.VISIBLE
            }

        }
    }

    private fun hideButton() {
        binding.submitNolHuruf.visibility = View.GONE
        binding.refreshNolHuruf.visibility = View.GONE
    }

    private fun showButton() {
        binding.submitNolHuruf.visibility = View.VISIBLE
        binding.refreshNolHuruf.visibility = View.VISIBLE
    }

    private fun submitMulti(idGame: Int, idSoal: Int,duration: Int, score: Int){
        binding.progressBarH0.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictHurufMultiPresenter.predictHurufMulti(idGame, idSoal, score,  duration, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        endGame(idGame)
                    }
            }
        }
    }

    private fun showScreen(id: String) {
        binding.progressBarH0.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id.toInt(), sessionManager.fetchAuthToken()!!).collectLatest {
                    showData(it)
                    showButton()
                    answer = it.jawaban[0]
                    binding.progressBarH0.visibility = View.INVISIBLE
                    refreshCanvas()
                }
            }
        }
    }

    private fun showData(data: Soal){
        speak(data.keterangan)
        binding.spkNolHuruf.setOnClickListener {
            speak(data.keterangan)
        }
        binding.soalHurufDipilih.text = data.keterangan
        binding.levelHurufKe.text = "Level ke ${data.idLevel}"
    }

    private fun refreshCanvasOnClick(){
        binding.refreshNolHuruf.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelNolHuruf.clearCanvas()
    }

    private fun back(){
        binding.levelNolHurufBack.setOnClickListener {
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
                        idGame
                    ), Template.getTopic(sessionManager.fetchIDRoom().toString()),
                    "high"
                )
                ).collectLatest {
                }
            }
        }
    }

    private fun listDialog(idGame: Int) {
        binding.btnUserParticipantH0.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    listUserParticipantPresenter.getListUserParticipant(idGame, sessionManager.fetchAuthToken()!!).collectLatest {
                        Template.listUser(it, this@HurufLevelNolActivity)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        return
    }

    override fun onStop() {
        super.onStop()
        this.finish()
    }

    override fun onPause() {
        super.onPause()
        this.finish()
    }
}