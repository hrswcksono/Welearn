package com.tugasakhir.welearn.presentation.ui.angka.canvas

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import cn.pedant.SweetAlert.SweetAlertDialog
import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.core.utils.CustomDialogBox
import com.tugasakhir.welearn.core.utils.Template
import com.tugasakhir.welearn.core.utils.Template.encodeImage
import com.tugasakhir.welearn.core.utils.Template.speak
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelTigaBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.entity.SoalEntity
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictAngkaPresenter
import com.tugasakhir.welearn.presentation.presenter.score.SoalByIDPresenter
import com.tugasakhir.welearn.presentation.ui.score.ui.ScoreAngkaUserActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class AngkaLevelTigaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOAL = "extra_soal"
        const val GAME_MODE = "game_mode"
        const val LEVEL_SOAL = "level_soal"
        const val ID_GAME = "id_game"
    }

    private lateinit var binding: ActivityAngkaLevelTigaBinding
    private val soalViewModel: SoalByIDPresenter by viewModel()
    private val predictAngkaPresenter: PredictAngkaPresenter by viewModel()
    private val predictAngkaMultiPresenter: PredictAngkaMultiPresenter by viewModel()
    private val joinGamePresenter: JoinGamePresenter by viewModel()
    private val endGamePresenter: EndGamePresenter by viewModel()
    private val pushNotification: PushNotificationPresenter by viewModel()
    private val listUserParticipantPresenter: UserParticipantPresenter by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelTigaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val mode = intent.getStringExtra(GAME_MODE)

        binding.levelTigaAngkaBack.setOnClickListener {
            onBackPressed()
        }

        handlingMode(mode.toString())

        refreshCanvasOnClick()
        back()

    }

    private fun handlingMode(mode: String) {
        if (mode == "multi") {
            enableButton()
            val soalID = intent.getStringExtra(LEVEL_SOAL)
            val arrayID = soalID.toString().split("|")
            val idGame = intent.getStringExtra(ID_GAME)
            listDialog(idGame!!.toInt())
            joinGame(idGame!!.toInt())
            var index = 0
            var total = 0L
            val begin = Date().time
//            Toast.makeText(this, idGame.toString(), Toast.LENGTH_SHORT).show()
            var idSoal = arrayID[index]
//            Toast.makeText(this, idSoal, Toast.LENGTH_SHORT).show()
            showScreen(idSoal)
            binding.submitTigaAngka.setOnClickListener {
                disableButton()
                val image = ArrayList<String>()
                image.add(encodeImage(binding.cnvsLevelTigaAngka.getBitmap())!!)
//                Toast.makeText(this, idSoal, Toast.LENGTH_SHORT).show()
                val end = Date().time
                total = (end - begin)/1000
//                Toast.makeText(this, total.toString(), Toast.LENGTH_SHORT).show()
                submitMulti(idGame.toInt(),idSoal.toInt(),total.toInt(), image)
                index++
                if (index < 3) {
                    idSoal = arrayID[index]
                    showScreen(idSoal)
                }

            }
        }else if (mode == "single") {
            binding.btnUserParticipantA3.visibility = View.INVISIBLE
            val idSoal = intent.getIntExtra(EXTRA_SOAL, 0).toString()
            showScreen(idSoal)
            binding.submitTigaAngka.setOnClickListener{
                val image = ArrayList<String>()
                image.add(encodeImage(binding.cnvsLevelTigaAngka.getBitmap())!!)
                disableButton()
                submitDrawing(idSoal, image)
            }
        }
    }

    private fun disableButton(){
        binding.submitTigaAngka.isEnabled = false
        binding.submitTigaAngka.isClickable = false
    }

    private fun enableButton(){
        binding.submitTigaAngka.isEnabled = true
        binding.submitTigaAngka.isClickable = true
    }

    private fun submitDrawing(id: String, image: ArrayList<String>) {
        binding.progressBarA3.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictAngkaPresenter.predictAngka(id.toInt(), image)
                    .collectLatest {
                        binding.progressBarA3.visibility = View.INVISIBLE
                        CustomDialogBox.withConfirm(
                            this@AngkaLevelTigaActivity,
                            SweetAlertDialog.SUCCESS_TYPE,
                            "Berhasil Menjawab",
                            it.message
                        ) {
                            startActivity(
                                Intent(
                                    this@AngkaLevelTigaActivity,
                                    ScoreAngkaUserActivity::class.java
                                )
                            )
                        }
                    }
            }
        }
    }

    private fun submitMulti(idGame: Int, idSoal: Int,duration: Int, image: ArrayList<String>){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictAngkaMultiPresenter.predictAngkaMulti(idGame, idSoal, image,  duration)
                    .collectLatest {
                        endGame(idGame)
                    }
            }
        }
    }

    private fun showScreen(idSoal: String) {
        binding.progressBarA3.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(idSoal.toInt()).collectLatest {
                    showData(it)
                    binding.progressBarA3.visibility = View.INVISIBLE
                    refreshCanvas()
                }
            }
        }
    }

    private fun showData(data: SoalEntity){
        speak(data.keterangan + " " + data.soal)
        binding.spkTigaAngka.setOnClickListener {
            speak(data.keterangan + " " + data.soal)
        }
        binding.soalAngkaDipilih.text = data.keterangan
        binding.levelAngkaKe.text = "Level ke ${data.idLevel}"
        binding.tvSoalAngkaTiga.text = data.soal
    }

    private fun refreshCanvasOnClick(){
        binding.refreshTigaAngka.setOnClickListener {
            refreshCanvas()
        }
    }

    private fun refreshCanvas(){
        binding.cnvsLevelTigaAngka.clearCanvas()
    }

    private fun back(){
        binding.levelTigaAngkaBack.setOnClickListener {
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
                ).collectLatest {  }
            }
        }
    }

    private fun listDialog(idGame: Int) {
        binding.btnUserParticipantA3.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    listUserParticipantPresenter.getListUserParticipant(idGame).collectLatest {
                        when(it) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                Template.listUser(it.data!!, this@AngkaLevelTigaActivity)
                            }
                            is Resource.Error -> {}
                        }
                    }
                }
            }
        }
    }
}