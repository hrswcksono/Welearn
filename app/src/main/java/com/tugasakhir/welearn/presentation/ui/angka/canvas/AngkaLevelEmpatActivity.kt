package com.tugasakhir.welearn.presentation.ui.angka.canvas

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import cn.pedant.SweetAlert.SweetAlertDialog
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.core.utils.CustomDialogBox
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelEmpatBinding
import com.tugasakhir.welearn.domain.model.NotificationData
import com.tugasakhir.welearn.domain.model.PushNotification
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.presentation.presenter.multiplayer.EndGameViewModel
import com.tugasakhir.welearn.presentation.presenter.multiplayer.JoinGameViewModel
import com.tugasakhir.welearn.presentation.presenter.multiplayer.PredictAngkaMultiViewModel
import com.tugasakhir.welearn.presentation.presenter.multiplayer.PushNotificationViewModel
import com.tugasakhir.welearn.presentation.presenter.singleplayer.PredictAngkaViewModel
import com.tugasakhir.welearn.presentation.ui.score.ui.ScoreAngkaUserActivity
import com.tugasakhir.welearn.presentation.presenter.score.SoalByIDViewModel
import com.tugasakhir.welearn.presentation.ui.score.ui.ScoreHurufUserActivity
import darren.googlecloudtts.GoogleCloudTTSFactory
import darren.googlecloudtts.parameter.AudioConfig
import darren.googlecloudtts.parameter.AudioEncoding
import darren.googlecloudtts.parameter.VoiceSelectionParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class AngkaLevelEmpatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAngkaLevelEmpatBinding
    private val viewModel: PredictAngkaViewModel by viewModel()
    private val soalViewModel: SoalByIDViewModel by viewModel()
    private val predictAngkaViewModel: PredictAngkaViewModel by viewModel()
    private val predictAngkaMultiViewModel: PredictAngkaMultiViewModel by viewModel()
    private val joinGameViewModel: JoinGameViewModel by viewModel()
    private val endGameViewModel: EndGameViewModel by viewModel()
    private val pushNotification: PushNotificationViewModel by viewModel()

    companion object {
        const val EXTRA_SOAL = "extra_soal"
        const val GAME_MODE = "game_mode"
        const val LEVEL_SOAL = "level_soal"
        const val ID_GAME = "id_game"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelEmpatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val mode = intent.getStringExtra(AngkaLevelNolActivity.GAME_MODE)

        handlingMode(mode.toString())

        supportActionBar?.hide()

        refreshCanvasOnClick()
        back()
    }

    private fun handlingMode(mode: String) {
        if (mode == "multi") {
            val soalID = intent.getStringExtra(LEVEL_SOAL)
            val arrayID = soalID.toString().split("|")
            val idGame = intent.getStringExtra(ID_GAME)
            joinGame(idGame!!.toInt())
            var index = 0
            var total = 0L
            val begin = Date().time
//            Toast.makeText(this, idGame.toString(), Toast.LENGTH_SHORT).show()
            var idSoal = arrayID[index]
//            Toast.makeText(this, idSoal, Toast.LENGTH_SHORT).show()
            showScreen(idSoal)
            binding.submitEmpatAngka.setOnClickListener {
                var image = ArrayList<String>()
                image.add(encodeImage(binding.cnvsLevelEmpatAngkaOne.getBitmap())!!)
                image.add(encodeImage(binding.cnvsLevelEmpatAngkaTwo.getBitmap())!!)
//                Toast.makeText(this, idSoal, Toast.LENGTH_SHORT).show()
                val end = Date().time
                total = (end - begin)/1000
                Toast.makeText(this, total.toString(), Toast.LENGTH_SHORT).show()
                submitMulti(idGame!!.toInt(),idSoal.toInt(),total.toInt(), image)
                index++
                if (index < 3) {
                    idSoal = arrayID[index]
                    showScreen(idSoal)
//                    submitDrawing(idSoal)
                }

            }
        }else if (mode == "single") {
            val idSoal = intent.getIntExtra(EXTRA_SOAL, 0).toString()
            showScreen(idSoal)
            binding.submitEmpatAngka.setOnClickListener{
                var image = ArrayList<String>()
                image.add(encodeImage(binding.cnvsLevelEmpatAngkaOne.getBitmap())!!)
                image.add(encodeImage(binding.cnvsLevelEmpatAngkaTwo.getBitmap())!!)
                submitDrawing(idSoal, image)
            }
        }
    }

    private fun submitMulti(idGame: Int, idSoal: Int,duration: Int, image: ArrayList<String>){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictAngkaMultiViewModel.predictAngkaMulti(idGame, idSoal, image,  duration)
                    .collectLatest {
                        endGame(idGame)
                    }
            }
        }
    }

    private fun submitDrawing(id: String, image: ArrayList<String>) {
        binding.progressBarA4.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictAngkaViewModel.predictAngka(id.toInt(), image)
                    .collectLatest {
                        binding.progressBarA4.visibility = View.INVISIBLE
                        CustomDialogBox.withConfirm(
                            this@AngkaLevelEmpatActivity,
                            SweetAlertDialog.SUCCESS_TYPE,
                            "Berhasil Menjawab",
                            it.message
                        ) {
                            startActivity(
                                Intent(
                                    this@AngkaLevelEmpatActivity,
                                    ScoreHurufUserActivity::class.java
                                )
                            )
                        }
                    }
            }
        }
    }

    private fun showScreen(id: String) {
        binding.progressBarA4.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id.toInt()).collectLatest {
                    showData(it)
                    binding.progressBarA4.visibility = View.INVISIBLE
                    refreshCanvas()
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
        binding.levelAngkaKe.text = "Level ke ${data.id_level}"
        binding.tvSoalAngkaEmpat.text = data.soal
    }

    private fun speak(string: String) {
        // Set the ApiKey and create GoogleCloudTTS.
        val googleCloudTTS = GoogleCloudTTSFactory.create(Constants.GOOGLE_API_KEY)
        googleCloudTTS.setVoiceSelectionParams(VoiceSelectionParams( "id-ID", "id-ID-Standard-A"))
            .setAudioConfig(AudioConfig(AudioEncoding.MP3, 1f , 10f));

        // start speak
        googleCloudTTS.start(string);
    }

    private fun encodeImage(bm: Bitmap): String? {
        val imgBitmap = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, imgBitmap)
        val b = imgBitmap.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
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

    private fun joinGame(idGame: Int){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Default) {
                joinGameViewModel.joinGame(idGame.toString())
                    .collectLatest {  }
            }
        }
    }

    private fun endGame(idGame: Int){
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                endGameViewModel.endGame(idGame.toString())
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
                            idGame
                        ),
                        Constants.TOPIC_JOIN_HURUF,
                        "high"
                    )
                ).collectLatest {  }
            }
        }
    }

}