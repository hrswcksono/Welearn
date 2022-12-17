package com.tugasakhir.welearn.presentation.ui.huruf.multiplayer.canvas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import cn.pedant.SweetAlert.SweetAlertDialog
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.core.utils.CustomDialogBox
import com.tugasakhir.welearn.core.utils.Template
import com.tugasakhir.welearn.core.utils.Template.encodeImage
import com.tugasakhir.welearn.core.utils.Template.speak
import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.databinding.ActivityHurufLevelDuaBinding
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.entity.SoalEntity
import com.tugasakhir.welearn.presentation.presenter.multiplayer.*
import com.tugasakhir.welearn.presentation.presenter.score.SoalByIDPresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictHurufPresenter
import com.tugasakhir.welearn.presentation.ui.score.ui.ScoreMultiplayerActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class HurufLevelDuaActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_SOAL = "extra_soal"
        const val GAME_MODE = "game_mode"
        const val LEVEL_SOAL = "level_soal"
        const val ID_GAME = "id_game"
        const val NO_SOAL = "no_soal"
    }

    private lateinit var binding: ActivityHurufLevelDuaBinding
    private val soalViewModel: SoalByIDPresenter by viewModel()
    private val predictHurufMultiPresenter: PredictHurufMultiPresenter by viewModel()
    private val predictHurufPresenter: PredictHurufPresenter by viewModel()
    private val joinGamePresenter: JoinGamePresenter by viewModel()
    private val endGamePresenter: EndGamePresenter by viewModel()
    private val pushNotification: PushNotificationPresenter by viewModel()
    private val listUserParticipantPresenter: UserParticipantPresenter by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufLevelDuaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val mode = intent.getStringExtra(GAME_MODE)

        binding.levelDuaHurufBack.setOnClickListener {
            onBackPressed()
        }

        binding.tvSelesaiH2.visibility = View.INVISIBLE
        handlingMode(mode.toString())
        refreshCanvasOnClick()
        back()

    }

    private fun handlingMode(mode: String) {
        if (mode == "multi") {
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
            binding.submitDuaHuruf.setOnClickListener {
                hideButton()
                val image = ArrayList<String>()
                image.apply {
                    add(encodeImage(binding.cnvsLevelDuaHurufone.getBitmap()))
                    add(encodeImage(binding.cnvsLevelDuaHuruftwo.getBitmap()))
                    add(encodeImage(binding.cnvsLevelDuaHurufthree.getBitmap()))
                    add(encodeImage(binding.cnvsLevelDuaHuruffour.getBitmap()))
                    add(encodeImage(binding.cnvsLevelDuaHuruffive.getBitmap()))
                }
                val end = Date().time
                total = (end - begin)/1000
                submitMulti(idGame.toInt(),idSoal.toInt(),total.toInt(), image)
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
        }else if (mode == "single") {
            val noSoal = intent.getStringExtra(NO_SOAL)
            binding.btnUserParticipantH2.visibility = View.INVISIBLE
            val idSoal = intent.getIntExtra(EXTRA_SOAL, 0).toString()
            showScreen(idSoal)
            binding.submitDuaHuruf.setOnClickListener{
                val image = ArrayList<String>()
                image.apply {
                    add(encodeImage(binding.cnvsLevelDuaHurufone.getBitmap()))
                    add(encodeImage(binding.cnvsLevelDuaHuruftwo.getBitmap()))
                    add(encodeImage(binding.cnvsLevelDuaHurufthree.getBitmap()))
                    add(encodeImage(binding.cnvsLevelDuaHuruffour.getBitmap()))
                    add(encodeImage(binding.cnvsLevelDuaHuruffive.getBitmap()))
                }
                submitDrawing(idSoal, image)
            }
        }
    }

    private fun hideButton() {
        binding.submitDuaHuruf.visibility = View.GONE
        binding.refreshDuaHuruf.visibility = View.GONE
    }

    private fun showButton() {
        binding.submitDuaHuruf.visibility = View.VISIBLE
        binding.refreshDuaHuruf.visibility = View.VISIBLE
    }

    private fun submitMulti(idGame: Int, idSoal: Int,duration: Int, image: ArrayList<String>){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictHurufMultiPresenter.predictHurufMulti(idGame, idSoal, image,  duration)
                    .collectLatest {
                        endGame(idGame)
                    }
            }
        }
    }

    private fun submitDrawing(id: String, image: ArrayList<String>) {
        binding.progressBarH2.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictHurufPresenter.predictHuruf(id.toInt(), image)
                    .collectLatest {
                        binding.progressBarH2.visibility = View.INVISIBLE
                        CustomDialogBox.withConfirm(
                            this@HurufLevelDuaActivity,
                            SweetAlertDialog.SUCCESS_TYPE,
                            "Berhasil Menjawab",
                            it.message
                        ) {
//                            startActivity(
//                                Intent(
//                                    this@HurufLevelDuaActivity,
//                                    ScoreHurufUserActivity::class.java
//                                )
//                            )
                        }
                    }
            }
        }
    }

    private fun showScreen(id: String) {
        binding.progressBarH2.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id.toInt()).collectLatest {
                    showData(it)
                    showButton()
                    binding.progressBarH2.visibility = View.INVISIBLE
                    refreshCanvas()
                }
            }
        }
    }

    private fun showData(data: SoalEntity){
        speak(data.keterangan)
        binding.spkDuaHuruf.setOnClickListener {
            speak(data.keterangan)
        }
        binding.soalHurufDipilih.text = data.keterangan
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
                        Constants.TOPIC_JOIN_HURUF,
                        "high"
                    )
                ).collectLatest {
                    val moveToScoreMulti = Intent(this@HurufLevelDuaActivity, ScoreMultiplayerActivity::class.java)
                    moveToScoreMulti.putExtra(ScoreMultiplayerActivity.ID_GAME, idGame)
                    startActivity(moveToScoreMulti)
                }
            }
        }
    }

    private fun listDialog(idGame: Int) {
        binding.btnUserParticipantH2.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    listUserParticipantPresenter.getListUserParticipant(idGame).collectLatest {
                        when(it) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                Template.listUser(it.data!!, this@HurufLevelDuaActivity)
                            }
                            is Resource.Error -> {}
                        }
                    }
                }
            }
        }
    }
}