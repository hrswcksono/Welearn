package com.tugasakhir.welearn.presentation.ui.angka.canvas

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelEmpatBinding
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.presentation.ui.angka.PredictAngkaViewModel
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

class AngkaLevelEmpatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAngkaLevelEmpatBinding
    private val viewModel: PredictAngkaViewModel by viewModel()
    private val soalViewModel: SoalAngkaByIDViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    companion object {
        const val EXTRA_SOAL = "extra_soal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelEmpatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val idSoal = intent.getIntExtra(EXTRA_SOAL, 0)
        sessionManager = SharedPreference(this)

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.soalAngkaByID(idSoal, sessionManager.fetchAuthToken().toString()).collectLatest {
                    show(it)
                }
            }
        }

        supportActionBar?.hide()

        binding.levelEmpatAngkaBack.setOnClickListener {
            onBackPressed()
        }

        draw()
    }

    private fun show(data: Soal){
        binding.spkEmpatAngka.setOnClickListener {
            speak(data.keterangan)
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

    private fun draw() {

        binding.submitEmpatAngka.setOnClickListener {
            Toast.makeText(this, encodeImage(binding.cnvsLevelEmpatAngkaOne.getBitmap()) + encodeImage(binding.cnvsLevelEmpatAngkaTwo.getBitmap()), Toast.LENGTH_LONG).show()
        }

        binding.refreshEmpatAngka.setOnClickListener {
            binding.cnvsLevelEmpatAngkaOne.clearCanvas()
            binding.cnvsLevelEmpatAngkaTwo.clearCanvas()
        }

    }

}