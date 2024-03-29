package com.tugasakhir.welearn.presentation.view.huruf.multiplayer.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import cn.pedant.SweetAlert.SweetAlertDialog
import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.utils.*
import com.tugasakhir.welearn.utils.Template.speak
import com.tugasakhir.welearn.databinding.ActivityHurufLevelDuaBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.entity.Soal
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictHurufPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class HurufLevelDuaActivity : AppCompatActivity() {
    companion object {
        const val LEVEL_SOAL = "level_soal"
        const val ID_GAME = "id_game"
    }

    private lateinit var binding: ActivityHurufLevelDuaBinding
    private val pushNotification: PushNotificationPresenter by viewModel()
    private val inGamePresenter: InGamePresenter by viewModel()
    private lateinit var sessionManager: SharedPreference
    private var answer: String ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufLevelDuaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.levelDuaHurufBack.setOnClickListener {
            onBackPressed()
        }

        sessionManager = SharedPreference(this)

        binding.tvSelesaiH2.visibility = View.INVISIBLE
        main()
        refreshCanvasOnClick()
        back()
        initializeCanvas()
    }

    private fun initializeCanvas(){
        binding.cnvsLevelDuaHurufone.setStrokeWidth(30f)
        binding.cnvsLevelDuaHuruftwo.setStrokeWidth(30f)
        binding.cnvsLevelDuaHurufthree.setStrokeWidth(30f)
        binding.cnvsLevelDuaHuruffour.setStrokeWidth(30f)
        binding.cnvsLevelDuaHuruffive.setStrokeWidth(30f)
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
        binding.submitDuaHuruf.setOnClickListener {
            hideButton()
            var score = 0
            val canvas1 = binding.cnvsLevelDuaHurufone.getBitmap().scale(224, 224)
            val canvas2 = binding.cnvsLevelDuaHuruftwo.getBitmap().scale(224, 224)
            val canvas3 = binding.cnvsLevelDuaHurufthree.getBitmap().scale(224, 224)
            val canvas4 = binding.cnvsLevelDuaHuruffour.getBitmap().scale(224, 224)
            val canvas5 = binding.cnvsLevelDuaHuruffive.getBitmap().scale(224, 224)
            val (result1, accuracy1) = Predict.predictHurufCoba(this, canvas1)
            val (result2, accuracy2) = Predict.predictHurufCoba(this, canvas2)
            val (result3, accuracy3) = Predict.predictHurufCoba(this, canvas3)
            val (result4, accuracy4) = Predict.predictHurufCoba(this, canvas4)
            val (result5, accuracy5) = Predict.predictHurufCoba(this, canvas5)
            if (result1 == answer?.get(0) && result2 == answer?.get(1) && result3 == answer?.get(2) && result4 == answer?.get(3)&& result5 == answer?.get(4)){
                score =  10
            }
            val end = Date().time
            total = (end - begin)/1000
            CustomDialogBox.dialogPredictCoba(
                this@HurufLevelDuaActivity,
                {},
                score,
                dialogText(result1, accuracy1, result2, accuracy2, result3, accuracy3, result4, accuracy4, result5, accuracy5)
            )
            submitMulti(idGame.toInt(),idSoal.toInt(),total.toInt(), score)
            index++
            if (index < 3) {
                idSoal = arrayID[index]
                showScreen(idSoal)
//                    submitDrawing(idSoal)
            } else if(index == 3) {
                binding.progressBarH2.visibility = View.VISIBLE
                binding.tvSelesaiH2.visibility = View.VISIBLE
            }
        }
    }

    private fun dialogText(answer1: Char, accuracy1: Float, answer2: Char, accuracy2: Float, answer3: Char, accuracy3: Float, answer4: Char, accuracy4: Float, answer5: Char, accuracy5: Float) : String {
        return "Jawaban kamu $answer1, $answer2, $answer3, $answer4, $answer5  dengan Ketelitian ${(accuracy1*100).toInt()}%, ${(accuracy2*100).toInt()}%, ${(accuracy3*100).toInt()}%, ${(accuracy4*100).toInt()}%, ${(accuracy5*100).toInt()}%\n"
    }

    private fun hideButton() {
        binding.submitDuaHuruf.visibility = View.GONE
        binding.refreshDuaHuruf.visibility = View.GONE
    }

    private fun showButton() {
        binding.submitDuaHuruf.visibility = View.VISIBLE
        binding.refreshDuaHuruf.visibility = View.VISIBLE
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
                                CustomDialogBox.dialogNoInternet(this@HurufLevelDuaActivity)
                            }
                        }
                    }
            }
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
                            CustomDialogBox.dialogNoInternet(this@HurufLevelDuaActivity)
                        }
                    }
                }
            }
        }
    }

    private fun dialogEndGame(idGame: Int){
        binding.btnEndGameH2.setOnClickListener {
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

    private fun showScreen(id: String) {
        binding.progressBarH2.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                inGamePresenter.getSoalByID(id.toInt(), sessionManager.fetchAuthToken()!!).collectLatest {
                    when(it) {
                        is Resource.Success ->{
                            showData(it.data!!)
                            showButton()
                            answer = it.data.jawaban
                            binding.progressBarH2.visibility = View.INVISIBLE
                            refreshCanvas()
                        }
                        is Resource.Loading ->{}
                        is Resource.Error ->{
                            CustomDialogBox.dialogNoInternet(this@HurufLevelDuaActivity)
                        }
                    }
                }
            }
        }
    }

    private fun showData(data: Soal){
        speak(data.keterangan)
        binding.spkDuaHuruf.setOnClickListener {
            speak(data.keterangan)
        }
        binding.soalHurufDipilih.text = "Tuliskan kata " + "\"" + data.jawaban + "\""
        binding.levelHurufKe.text = "Level ke ${data.idLevel}"
    }

    private fun refreshCanvasOnClick(){
        binding.refreshDuaHuruf.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelDuaHurufone.clearCanvas()
        binding.cnvsLevelDuaHuruftwo.clearCanvas()
        binding.cnvsLevelDuaHurufthree.clearCanvas()
        binding.cnvsLevelDuaHuruffour.clearCanvas()
        binding.cnvsLevelDuaHuruffive.clearCanvas()
    }

    private fun back(){
        binding.levelDuaHurufBack.setOnClickListener {
            onBackPressed()
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
                                CustomDialogBox.dialogNoInternet(this@HurufLevelDuaActivity)
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

    private fun listDialog(idGame: Int) {
        binding.btnUserParticipantH2.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    inGamePresenter.getListUserParticipant(idGame, sessionManager.fetchAuthToken()!!).collectLatest {
                        when(it) {
                            is Resource.Success ->{
                                Template.listUser(it.data!!, this@HurufLevelDuaActivity)
                            }
                            is Resource.Loading ->{}
                            is Resource.Error ->{
                                CustomDialogBox.dialogNoInternet(this@HurufLevelDuaActivity)
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