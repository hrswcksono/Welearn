package com.tugasakhir.welearn.presentation.view.huruf.multiplayer.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
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
        binding.cnvsLevelTigaHuruffive.setStrokeWidth(30f)
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
            val canvas5 = binding.cnvsLevelTigaHuruffive.getBitmap().scale(224, 224)
            val canvas6 = binding.cnvsLevelTigaHurufsix.getBitmap().scale(224, 224)
            val canvas7 = binding.cnvsLevelTigaHurufseven.getBitmap().scale(224, 224)
            val canvas8 = binding. cnvsLevelTigaHurufeight.getBitmap().scale(224, 224)
            val canvas9 = binding.cnvsLevelTigaHurufnine.getBitmap().scale(224, 224)
            val result1 = Predict.predictHuruf(this, canvas1, answer?.get(0)!!)
            val result2 = Predict.predictHuruf(this, canvas2, answer?.get(1)!!)
            val result3 = Predict.predictHuruf(this, canvas3, answer?.get(2)!!)
            val result4 = Predict.predictHuruf(this, canvas4, answer?.get(3)!!)
            val result5 = Predict.predictHuruf(this, canvas5, answer?.get(4)!!)
            val result6 = Predict.predictHuruf(this, canvas6, answer?.get(5)!!)
            val result7 = Predict.predictHuruf(this, canvas7, answer?.get(6)!!)
            val result8 = Predict.predictHuruf(this, canvas8, answer?.get(7)!!)
            val result9 = Predict.predictHuruf(this, canvas9, answer?.get(8)!!)
            if ((result1 + result2 + result3 + result4 + result5 + result6 +result7 + result8 +result9) == 90){
                score = 10
            }
            val end = Date().time
            total = (end - begin)/1000
            CustomDialogBox.dialogPredict(
                this@HurufLevelTigaActivity,
                {},
                score,
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
                inGamePresenter.predictHurufMulti(idGame, idSoal, score,  duration, sessionManager.fetchAuthToken()!!)
                    .collectLatest {
                        endGame(idGame)
                    }
            }
        }
    }

    private fun showScreen(id: String) {
        binding.progressBarH3.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                inGamePresenter.getSoalByID(id.toInt(), sessionManager.fetchAuthToken()!!).collectLatest {
                    show(it)
                    showButton()
                    answer = it.jawaban
                    binding.progressBarH3.visibility = View.INVISIBLE
                    refreshCanvas()
                }
            }
        }
    }

    private fun show(data: Soal){
        speak(data.keterangan)
        binding.spkTigaHuruf.setOnClickListener {
            speak(data.keterangan)
        }
        binding.soalHurufDipilih.text = data.keterangan
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
        binding.cnvsLevelTigaHuruffive.clearCanvas()
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

    private fun listDialog(idGame: Int) {
        binding.btnUserParticipantH3.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    inGamePresenter.getListUserParticipant(idGame, sessionManager.fetchAuthToken()!!).collectLatest {
                        Template.listUser(it, this@HurufLevelTigaActivity)
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