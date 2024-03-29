package com.tugasakhir.welearn.presentation.view.angka.multiplayer.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import cn.pedant.SweetAlert.SweetAlertDialog
import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.utils.*
import com.tugasakhir.welearn.utils.Template.speak
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelEmpatBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.entity.Soal
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class AngkaLevelEmpatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAngkaLevelEmpatBinding
    private val pushNotification: PushNotificationPresenter by viewModel()
    private val inGamePresenter: InGamePresenter by viewModel()
    private var answer: String ?= null
    private lateinit var sessionManager: SharedPreference

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
        sessionManager = SharedPreference(this)

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
        dialogEndGame(idGame.toInt())
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
            val (result1, accuracy1) = Predict.PredictAngkaCoba(this, canvas1)
            val (result2, accuracy2) = Predict.PredictAngkaCoba(this, canvas2)
            if (result1 == answer?.get(0) && result2 == answer?.get(1)){
                score = 10
            }
            val end = Date().time
            total = (end - begin)/1000
            CustomDialogBox.dialogPredictCoba(
                this@AngkaLevelEmpatActivity,
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
            } else if (index == 3) {
                binding.progressBarA4.visibility = View.VISIBLE
                binding.tvSelesaiA4.visibility = View.VISIBLE
            }

        }
    }

    private fun dialogText(answer1: Char, accuracy1: Float, answer2: Char, accuracy2: Float) : String {
        return "Jawaban kamu $answer1  Ketelitian ${(accuracy1*100).toInt()}%\n" +
                "Jawaban kamu $answer2  Ketelitian ${(accuracy2*100).toInt()}%"
    }

    private fun submitMulti(idGame: Int, idSoal: Int,duration: Int, score: Int){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                inGamePresenter.savePredictAngkaMulti(idGame, idSoal, score,  duration, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        when(it) {
                            is Resource.Success ->{
                                endGame(idGame)
                            }
                            is Resource.Loading ->{}
                            is Resource.Error ->{
                                CustomDialogBox.dialogNoInternet(this@AngkaLevelEmpatActivity)
                            }
                        }
                    }
            }
        }
    }

    private fun showScreen(id: String) {
        binding.progressBarA4.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                inGamePresenter.getSoalByID(id.toInt(), sessionManager.fetchAuthToken()!!).collectLatest {
                    when(it) {
                        is Resource.Success ->{
                            showData(it.data!!)
                            showButton()
                            answer = it.data.jawaban
                            binding.progressBarA4.visibility = View.INVISIBLE
                            refreshCanvas()
                        }
                        is Resource.Loading ->{}
                        is Resource.Error ->{
                            CustomDialogBox.dialogNoInternet(this@AngkaLevelEmpatActivity)
                        }
                    }
                }
            }
        }
    }

    private fun showData(data: Soal){
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

    private fun endGameByClick(idGame: Int){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                inGamePresenter.forceEndGame(idGame.toString(), sessionManager.fetchAuthToken()!!).collectLatest {
                    when(it) {
                        is Resource.Success ->{
                            if (it.data == "Room Berhasil Ditutup"){
                                showScoreMulti(idGame.toString())
                            }
                        }
                        is Resource.Loading ->{}
                        is Resource.Error ->{
                            CustomDialogBox.dialogNoInternet(this@AngkaLevelEmpatActivity)
                        }
                    }
                }
            }
        }
    }

    private fun dialogEndGame(idGame: Int){
        binding.btnEndGameA4.setOnClickListener {
            CustomDialogBox.withCancel(
                this,
                SweetAlertDialog.WARNING_TYPE,
                "Tombol untuk menutup Room",
                "Hanya gunakan jika ada masalah handphone pada teman anda",
                "Akhiri",
            ) {
                endGameByClick(idGame)
            }
        }
    }

    private fun endGame(idGame: Int){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                inGamePresenter.gameAlreadyEnd(idGame.toString(), sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        when(it) {
                            is Resource.Success ->{
                                if (it.data == "Room Berhasil Ditutup"){
//                            Toast.makeText(this@HurufLevelNolActivity, "Pindah", Toast.LENGTH_SHORT).show()
                                    showScoreMulti(idGame.toString())
                                }
                            }
                            is Resource.Loading ->{}
                            is Resource.Error ->{
                                CustomDialogBox.dialogNoInternet(this@AngkaLevelEmpatActivity)
                            }
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
                    inGamePresenter.getListUserParticipant(idGame, sessionManager.fetchAuthToken()!!).collectLatest {
                        when(it) {
                            is Resource.Success ->{
                                Template.listUser(it.data!!, this@AngkaLevelEmpatActivity)
                            }
                            is Resource.Loading ->{}
                            is Resource.Error ->{
                                CustomDialogBox.dialogNoInternet(this@AngkaLevelEmpatActivity)
                            }
                        }
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