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
import com.tugasakhir.welearn.databinding.ActivityHurufLevelTigaBinding
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

class HurufLevelTigaActivity : AppCompatActivity() {
    companion object {
        const val LEVEL_SOAL = "level_soal"
        const val ID_GAME = "id_game"
    }

    private lateinit var binding: ActivityHurufLevelTigaBinding
    private val pushNotification: PushNotificationPresenter by viewModel()
    private val inGamePresenter: InGamePresenter by viewModel()
    private lateinit var sessionManager: SharedPreference
    private var answer: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufLevelTigaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.levelTigaHurufBack.setOnClickListener {
            onBackPressed()
        }

        sessionManager = SharedPreference(this)

        binding.tvSelesaiH3.visibility = View.GONE
        draw()
        main()
        refreshCanvasOnClick()
        back()
        initializeCanvas()
    }

    private fun initializeCanvas(){
        binding.cnvsLevelTigaHurufone.setStrokeWidth(30f)
        binding.cnvsLevelTigaHuruftwo.setStrokeWidth(30f)
        binding.cnvsLevelTigaHurufthree.setStrokeWidth(30f)
        binding.cnvsLevelTigaHuruffour.setStrokeWidth(30f)
//        binding.cnvsLevelTigaHuruffive.setStrokeWidth(30f)
        binding.cnvsLevelTigaHurufsix.setStrokeWidth(30f)
        binding.cnvsLevelTigaHurufseven.setStrokeWidth(30f)
        binding.cnvsLevelTigaHurufeight.setStrokeWidth(30f)
        binding.cnvsLevelTigaHurufnine.setStrokeWidth(30f)
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
        binding.submitTigaHuruf.setOnClickListener {
            hideButton()
            var score = 0
            val canvas1 = binding.cnvsLevelTigaHurufone.getBitmap().scale(224, 224)
            val canvas2 = binding.cnvsLevelTigaHuruftwo.getBitmap().scale(224, 224)
            val canvas3 = binding.cnvsLevelTigaHurufthree.getBitmap().scale(224, 224)
            val canvas4 = binding.cnvsLevelTigaHuruffour.getBitmap().scale(224, 224)
//            val canvas5 = binding.cnvsLevelTigaHuruffive.getBitmap().scale(224, 224)
            val canvas6 = binding.cnvsLevelTigaHurufsix.getBitmap().scale(224, 224)
            val canvas7 = binding.cnvsLevelTigaHurufseven.getBitmap().scale(224, 224)
            val canvas8 = binding. cnvsLevelTigaHurufeight.getBitmap().scale(224, 224)
            val canvas9 = binding.cnvsLevelTigaHurufnine.getBitmap().scale(224, 224)
            val (result1, accuracy1) = Predict.predictHurufCoba(this, canvas1)
            val (result2, accuracy2) = Predict.predictHurufCoba(this, canvas2)
            val (result3, accuracy3) = Predict.predictHurufCoba(this, canvas3)
            val (result4, accuracy4) = Predict.predictHurufCoba(this, canvas4)
//            val (result5, accuracy5) = Predict.predictHurufCoba(this, canvas5)
            val (result6, accuracy6) = Predict.predictHurufCoba(this, canvas6)
            val (result7, accuracy7) = Predict.predictHurufCoba(this, canvas7)
            val (result8, accuracy8) = Predict.predictHurufCoba(this, canvas8)
            val (result9, accuracy9) = Predict.predictHurufCoba(this, canvas9)
//            if (canvas5 == null){
//                if (result1 == answer?.get(0) && result2 == answer?.get(1) && result3 == answer?.get(2) && result4 == answer?.get(3) && result6 == answer?.get(5)&& result7 == answer?.get(6)&& result8 == answer?.get(7)&& result9 == answer?.get(8)){
//                    score = 10
//                }else{
//                    0
//                }
//                message = dialogTextOnCanvas(result1, accuracy1, result2, accuracy2, result3, accuracy3, result4, accuracy4, result6, accuracy6, result7, accuracy7, result8, accuracy8, result9, accuracy9)
//            }else{
//                score = 0
//                message = "perhatikan lagi letak spasi, cukup dikosongi saja"
//            }
            if (result1 == answer?.get(0) && result2 == answer?.get(1) && result3 == answer?.get(2) && result4 == answer?.get(3) && result6 == answer?.get(5)&& result7 == answer?.get(6)&& result8 == answer?.get(7)&& result9 == answer?.get(8)){
                score = 10
            }
            var message: String = dialogTextOnCanvas(result1, accuracy1, result2, accuracy2, result3, accuracy3, result4, accuracy4, result6, accuracy6, result7, accuracy7, result8, accuracy8, result9, accuracy9)
            val end = Date().time
            total = (end - begin)/1000
            CustomDialogBox.dialogPredictCoba(
                this@HurufLevelTigaActivity,
                {},
                score,
                message
            )
            submitMulti(idGame.toInt(),idSoal.toInt(),total.toInt(), score)
            index++
            if (index < 3) {
                idSoal = arrayID[index]
                showScreen(idSoal)
//                    submitDrawing(idSoal)
            }else if (index == 3) {
                binding.progressBarH3.visibility = View.VISIBLE
                binding.tvSelesaiH3.visibility = View.VISIBLE
            }
        }
    }

//    private fun dialogText(answer1: Char, accuracy1: Float, answer2: Char, accuracy2: Float, answer3: Char, accuracy3: Float, answer4: Char, accuracy4: Float, answer5: Char, accuracy5: Float, answer6: Char, accuracy6: Float, answer7: Char, accuracy7: Float, answer8: Char, accuracy8: Float, answer9: Char, accuracy9: Float) : String {
//        return "Jawaban kamu $answer1, $answer2, $answer3, $answer4, $answer5, $answer6, $answer7, $answer8, $answer9  dengan Ketelitian ${(accuracy1*100).toInt()}%, ${(accuracy2*100).toInt()}%, ${(accuracy3*100).toInt()}%, ${(accuracy4*100).toInt()}%, ${(accuracy5*100).toInt()}%, ${(accuracy6*100).toInt()}%, ${(accuracy7*100).toInt()}%, ${(accuracy8*100).toInt()}%, ${(accuracy9*100).toInt()}%\n"
//    }

    private fun dialogTextOnCanvas(answer1: Char, accuracy1: Float, answer2: Char, accuracy2: Float, answer3: Char, accuracy3: Float, answer4: Char, accuracy4: Float , answer6: Char, accuracy6: Float, answer7: Char, accuracy7: Float, answer8: Char, accuracy8: Float, answer9: Char, accuracy9: Float) : String {
        return "Jawaban kamu $answer1, $answer2, $answer3, $answer4, $answer6, $answer7, $answer8, $answer9  dengan Ketelitian ${(accuracy1*100).toInt()}%, ${(accuracy2*100).toInt()}%, ${(accuracy3*100).toInt()}%, ${(accuracy4*100).toInt()}%, ${(accuracy6*100).toInt()}%, ${(accuracy7*100).toInt()}%, ${(accuracy8*100).toInt()}%, ${(accuracy9*100).toInt()}%\n"
    }

    private fun hideButton() {
        binding.submitTigaHuruf.visibility = View.GONE
        binding.refreshTigaHuruf.visibility = View.GONE
    }

    private fun showButton() {
        binding.submitTigaHuruf.visibility = View.VISIBLE
        binding.refreshTigaHuruf.visibility = View.VISIBLE
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
                                CustomDialogBox.dialogNoInternet(this@HurufLevelTigaActivity)
                            }
                        }
                    }
            }
        }
    }

    private fun showScreen(id: String) {
        binding.progressBarH3.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                inGamePresenter.getSoalByID(id.toInt(), sessionManager.fetchAuthToken()!!).collectLatest {
                    when(it) {
                        is Resource.Success ->{
                            show(it.data!!)
                            showButton()
                            answer = it.data.jawaban
                            binding.progressBarH3.visibility = View.INVISIBLE
                            refreshCanvas()
                        }
                        is Resource.Loading ->{}
                        is Resource.Error ->{
                            CustomDialogBox.dialogNoInternet(this@HurufLevelTigaActivity)
                        }
                    }
                }
            }
        }
    }

    private fun show(data: Soal){
        speak(data.keterangan)
        binding.spkTigaHuruf.setOnClickListener {
            speak(data.keterangan)
        }
        binding.soalHurufDipilih.text = "Tuliskan kata " + "\"" + data.jawaban + "\""
        binding.levelHurufKe.text = "Level ke ${data.idLevel}"
    }

    private fun draw() {
    }

    private fun refreshCanvasOnClick(){
        binding.refreshTigaHuruf.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelTigaHurufone.clearCanvas()
        binding.cnvsLevelTigaHuruftwo.clearCanvas()
        binding.cnvsLevelTigaHurufthree.clearCanvas()
        binding.cnvsLevelTigaHuruffour.clearCanvas()
//        binding.cnvsLevelTigaHuruffive.clearCanvas()
        binding.cnvsLevelTigaHurufsix.clearCanvas()
        binding.cnvsLevelTigaHurufseven.clearCanvas()
        binding.cnvsLevelTigaHurufeight.clearCanvas()
        binding.cnvsLevelTigaHurufnine.clearCanvas()
    }

    private fun back(){
        binding.levelTigaHurufBack.setOnClickListener {
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
                                CustomDialogBox.dialogNoInternet(this@HurufLevelTigaActivity)
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
        binding.btnUserParticipantH3.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    inGamePresenter.getListUserParticipant(idGame, sessionManager.fetchAuthToken()!!).collectLatest {
                        when(it) {
                            is Resource.Success ->{
                                Template.listUser(it.data!!, this@HurufLevelTigaActivity)
                            }
                            is Resource.Loading ->{}
                            is Resource.Error ->{
                                CustomDialogBox.dialogNoInternet(this@HurufLevelTigaActivity)
                            }
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
                            CustomDialogBox.dialogNoInternet(this@HurufLevelTigaActivity)
                        }
                    }
                }
            }
        }
    }

    private fun dialogEndGame(idGame: Int){
        binding.btnEndGameH3.setOnClickListener {
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