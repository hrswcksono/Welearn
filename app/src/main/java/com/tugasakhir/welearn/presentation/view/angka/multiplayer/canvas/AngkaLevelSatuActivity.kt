package com.tugasakhir.welearn.presentation.view.angka.multiplayer.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import com.tugasakhir.welearn.utils.*
import com.tugasakhir.welearn.utils.Template.speak
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelSatuBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.entity.Soal
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictAngkaPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class AngkaLevelSatuActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_SOAL = "extra_soal"
        const val GAME_MODE = "game_mode"
        const val LEVEL_SOAL = "level_soal"
        const val ID_GAME = "id_game"
        const val NO_SOAL = "no_soal"
    }

    private lateinit var binding: ActivityAngkaLevelSatuBinding
    private val inGamePresenter: InGamePresenter by viewModel()
    private val pushNotification: PushNotificationPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference
    private var answer: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelSatuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        sessionManager = SharedPreference(this)


        binding.levelSatuAngkaBack.setOnClickListener {
            onBackPressed()
        }
        binding.tvSelesaiA1.visibility = View.INVISIBLE

//        binding.apply {
//            cnvsLevelSatuAngka.setBackgroundColor(Color.BLACK)
//            cnvsLevelSatuAngka.setColor(Color.WHITE)
//        }

        main()
        refreshCanvasOnClick()
        back()
        initializeCanvas()
    }

    private fun initializeCanvas() {
        binding.cnvsLevelSatuAngkaOne.setStrokeWidth(30f)
        binding.cnvsLevelSatuAngkaTwo.setStrokeWidth(30f)
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
        binding.submitSatuAngka.setOnClickListener {
            hideButton()
            var score = 0
            val canvas1 = binding.cnvsLevelSatuAngkaOne.getBitmap().scale(224, 224)
            val canvas2 = binding.cnvsLevelSatuAngkaTwo.getBitmap().scale(224, 224)
            val (result1, accuracy1) = Predict.PredictAngkaCoba(this, canvas1)
            val (result2, accuracy2) = Predict.PredictAngkaCoba(this, canvas2)
            if (result1 == answer?.get(0) && result2 == answer?.get(1)){
                score = 10
            }
            val end = Date().time
            total = (end - begin)/1000
            CustomDialogBox.dialogPredictCoba(
                this@AngkaLevelSatuActivity,
                {},
                score,
                dialogText(result1, accuracy1, result2, accuracy2)
            )
            submitMulti(idGame.toInt(),idSoal.toInt(),total.toInt(), score)
            index++
            if (index < 3) {
                idSoal = arrayID[index]
                showScreen(idSoal)
//                    submitDrawing(idSoal)
            }else if (index == 3){
                binding.progressBarA1.visibility = View.VISIBLE
                binding.tvSelesaiA1.visibility = View.VISIBLE
            }

        }
    }

    private fun dialogText(answer1: Char, accuracy1: Float, answer2: Char, accuracy2: Float) : String {
        return "Jawaban kamu $answer1 dengan Ketelitian ${(accuracy1*100).toInt()}%\n" +
                "Jawaban kamu $answer2 dengan Ketelitian ${(accuracy2*100).toInt()}%"
    }

    private fun submitMulti(idGame: Int, idSoal: Int,duration: Int, score: Int){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                inGamePresenter.savePredictAngkaMulti(idGame, idSoal, score,  duration, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        endGame(idGame)
                    }
            }
        }
    }

    private fun showScreen(id: String) {
        binding.progressBarA1.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                inGamePresenter.getSoalByID(id.toInt(), sessionManager.fetchAuthToken()!!).collectLatest {
                    showData(it)
                    showButton()
                    answer = it.jawaban
                    binding.progressBarA1.visibility = View.INVISIBLE
                    refreshCanvas()
                }
            }
        }
    }

    private fun showData(data: Soal){
        speak(data.keterangan + " " + data.soal)
        binding.spkSatuAngka.setOnClickListener {
            speak(data.keterangan + " " + data.soal)
        }

        binding.soalAngkaDipilih.text = data.keterangan
        binding.levelAngkaKe.text = "Level ke ${data.idLevel}"
        binding.tvSoalAngkaSatu.text = data.soal
    }

    private fun refreshCanvasOnClick(){
        binding.refreshSatuAngka.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelSatuAngkaOne.clearCanvas()
        binding.cnvsLevelSatuAngkaTwo.clearCanvas()
    }

    private fun back(){
        binding.levelSatuAngkaBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun endGame(idGame: Int){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                inGamePresenter.gameAlreadyEnd(idGame.toString(), sessionManager.fetchAuthToken()!!)
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
                        ),
                        Template.getTopic(sessionManager.fetchIDRoom().toString()),
                        "high"
                    )
                ).collectLatest {
                }
            }
        }
    }

    private fun hideButton() {
        binding.submitSatuAngka.visibility = View.GONE
        binding.refreshSatuAngka.visibility = View.GONE
    }

    private fun showButton() {
        binding.submitSatuAngka.visibility = View.VISIBLE
        binding.refreshSatuAngka.visibility = View.VISIBLE
    }

    private fun listDialog(idGame: Int) {
        binding.btnUserParticipantA1.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    inGamePresenter.getListUserParticipant(idGame, sessionManager.fetchAuthToken()!!).collectLatest {
                        Template.listUser(it, this@AngkaLevelSatuActivity)
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
    }
}