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
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelDuaBinding
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

class AngkaLevelDuaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOAL = "extra_soal"
    }

    private lateinit var binding: ActivityAngkaLevelDuaBinding
    private val viewModel: PredictAngkaViewModel by viewModel()
    private val soalViewModel: SoalAngkaByIDViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelDuaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val idSoal = intent.getIntExtra(EXTRA_SOAL, 0)
        sessionManager = SharedPreference(this)

        binding.levelDuaAngkaBack.setOnClickListener {
            onBackPressed()
        }

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.soalAngkaByID(idSoal, sessionManager.fetchAuthToken().toString()).collectLatest {
                    show(it)
                }
            }
        }
        draw()
    }

    private fun show(data: Soal){
        binding.spkDuaAngka.setOnClickListener {
            speak(data.keterangan)
        }
        binding.soalAngkaDipilih.text = data.keterangan
        binding.levelAngkaKe.text = "Level ke ${data.id_level}"
        binding.tvSoalAngkaDua.text = data.soal
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
        binding.refreshDuaAngka.setOnClickListener {
            binding.cnvsLevelDuaAngka.clearCanvas()
        }

        binding.submitDuaAngka.setOnClickListener {
            Toast.makeText(this, encodeImage(binding.cnvsLevelDuaAngka.getBitmap()), Toast.LENGTH_LONG).show()
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