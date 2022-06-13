package com.tugasakhir.welearn.presentation.ui.angka.canvas

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import cn.pedant.SweetAlert.SweetAlertDialog
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelTigaBinding
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.presentation.ui.angka.PredictAngkaViewModel
import com.tugasakhir.welearn.presentation.ui.score.ui.ScoreAngkaUserActivity
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

class AngkaLevelTigaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOAL = "extra_soal"
        const val GAME_MODE = "game_mode"
        const val LEVEL_SOAL = "level_soal"
    }

    private lateinit var binding: ActivityAngkaLevelTigaBinding
    private val viewModel: PredictAngkaViewModel by viewModel()
    private val soalViewModel: SoalAngkaByIDViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelTigaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val mode = intent.getStringExtra(GAME_MODE)
        sessionManager = SharedPreference(this)

        binding.levelTigaAngkaBack.setOnClickListener {
            onBackPressed()
        }

        handlingMode(mode.toString())


        refreshCanvasOnClick()
        back()

    }

    private fun handlingMode(mode: String) {
        if (mode == "multi") {
            val soalID = intent.getStringExtra(LEVEL_SOAL)
            val arrayID = soalID.toString().split("|")
            var index = 0
//            Toast.makeText(this, soalID, Toast.LENGTH_SHORT).show()
            var idSoal = arrayID[index]
            showScreen(idSoal)
            binding.submitTigaAngka.setOnClickListener {
                index++
                idSoal = arrayID[index]
                showScreen(idSoal)
                submitDrawing(idSoal)
            }
        }else if (mode == "single") {
            val idSoal = intent.getIntExtra(EXTRA_SOAL, 0).toString()
            showScreen(idSoal)
            binding.submitTigaAngka.setOnClickListener{
                submitDrawing(idSoal)
            }
        }
    }

    private fun submitDrawing(idSoal: String) {

    }

    private fun showScreen(idSoal: String) {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.soalAngkaByID(idSoal.toInt(), sessionManager.fetchAuthToken().toString()).collectLatest {
                    showData(it)
                    refreshCanvas()
                }
            }
        }
    }

    private fun showData(data: Soal){
        binding.spkTigaAngka.setOnClickListener {
            speak(data.keterangan)
        }
        binding.soalAngkaDipilih.text = data.keterangan
        binding.levelAngkaKe.text = "Level ke ${data.id_level}"
        binding.tvSoalAngkaTiga.text = data.soal
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

    private fun draw(){
        binding.refreshTigaAngka.setOnClickListener {
            binding.cnvsLevelTigaAngka.clearCanvas()
        }

        binding.submitTigaAngka.setOnClickListener {
            Toast.makeText(this, encodeImage(binding.cnvsLevelTigaAngka.getBitmap()), Toast.LENGTH_LONG).show()
        }
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

    private fun alert(string: String, body: String){
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText(string)
            .setContentText(body)
            .setConfirmText("Lihat Skor")
            .setConfirmClickListener {
                    sDialog -> sDialog.dismissWithAnimation()
                startActivity(Intent(this, ScoreAngkaUserActivity::class.java))
            }
            .show()
    }
}