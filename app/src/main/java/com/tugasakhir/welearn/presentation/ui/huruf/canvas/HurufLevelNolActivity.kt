package com.tugasakhir.welearn.presentation.ui.huruf.canvas

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
import com.tugasakhir.welearn.databinding.ActivityHurufLevelNolBinding
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.presentation.presenter.multiplayer.PredictHurufMultiViewModel
import com.tugasakhir.welearn.presentation.ui.angka.canvas.AngkaLevelNolActivity
import com.tugasakhir.welearn.presentation.ui.huruf.PredictHurufViewModel
import com.tugasakhir.welearn.presentation.presenter.score.SoalByIDViewModel
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

class HurufLevelNolActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOAL = "extra_soal"
        const val GAME_MODE = "game_mode"
        const val LEVEL_SOAL = "level_soal"
    }

    private lateinit var binding: ActivityHurufLevelNolBinding
    private val viewModel: PredictHurufViewModel by viewModel()
    private val soalViewModel: SoalByIDViewModel by viewModel()
    private val predictHurufMultiViewModel: PredictHurufMultiViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufLevelNolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val mode = intent.getStringExtra(GAME_MODE)

        sessionManager = SharedPreference(this)

        handlingMode(mode.toString())
        refreshCanvasOnClick()
        back()
    }

    private fun handlingMode(mode: String) {
        if (mode == "multi") {
            val soalID = intent.getStringExtra(LEVEL_SOAL)
            val arrayID = soalID.toString().split("|")
            var index = 0
            val begin = System.currentTimeMillis()
            Toast.makeText(this, soalID, Toast.LENGTH_SHORT).show()
            var idSoal = arrayID[index]
//            Toast.makeText(this, idSoal, Toast.LENGTH_SHORT).show()
            showScreen(idSoal)
            binding.submitNolHuruf.setOnClickListener {
                Toast.makeText(this, idSoal, Toast.LENGTH_SHORT).show()
                index++
                if (index < 3) {
                    idSoal = arrayID[index]
                    showScreen(idSoal)
                    submitDrawing(idSoal)
                } else {
                    submitMulti(begin)
                }
            }
        }else if (mode == "single") {
            val idSoal = intent.getIntExtra(AngkaLevelNolActivity.EXTRA_SOAL, 0).toString()
            showScreen(idSoal)
            binding.submitNolHuruf.setOnClickListener{
                submitDrawing(idSoal)
            }
        }
    }

    private fun submitDrawing(id: String) {

    }

    private fun submitMulti(duration: Long){
        val end = System.currentTimeMillis()
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                predictHurufMultiViewModel.predictHurufMulti(1, 1, (end-duration).toInt(), sessionManager.fetchAuthToken().toString())
                    .collectLatest {
//                        CustomDialogBox.onlyTitle(
//                            this@HurufLevelNolActivity,
//                            SweetAlertDialog.SUCCESS_TYPE,
//                            "Berhasil Submit"
//                        ) {
//
//                        }
                    }
            }
        }
    }

    private fun showScreen(id: String) {
        binding.progressBarH0.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.getSoalByID(id.toInt(), sessionManager.fetchAuthToken().toString()).collectLatest {
                    showData(it)
                    binding.progressBarH0.visibility = View.INVISIBLE
                    refreshCanvas()
                }
            }
        }
    }

    private fun speak(string: String) {
        // Set the ApiKey and create GoogleCloudTTS.
        val googleCloudTTS = GoogleCloudTTSFactory.create(Constants.GOOGLE_API_KEY)
        googleCloudTTS.setVoiceSelectionParams(VoiceSelectionParams( "id-ID", "id-ID-Standard-A"))
            .setAudioConfig(AudioConfig(AudioEncoding.MP3, 1f , 10f));

        // start speak
        googleCloudTTS.start(string);
    }

    private fun showData(data: Soal){
        speak(data.keterangan + " " + data.soal)
        binding.spkNolHuruf.setOnClickListener {
            speak(data.keterangan + " " + data.soal)
        }
        binding.soalHurufDipilih.text = data.keterangan
        binding.levelHurufKe.text = "Level ke ${data.id_level}"
    }

    private fun encodeImage(bm: Bitmap): String? {
        val imgBitmap = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, imgBitmap)
        val b = imgBitmap.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
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

}