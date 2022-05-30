package com.tugasakhir.welearn.presentation.ui.huruf.canvas

import android.graphics.Bitmap
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import android.view.View.OnTouchListener
import androidx.appcompat.app.AppCompatActivity
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.databinding.ActivityHurufLevelTigaBinding
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.presentation.ui.huruf.PredictHurufViewModel
import darren.googlecloudtts.GoogleCloudTTSFactory
import darren.googlecloudtts.parameter.AudioConfig
import darren.googlecloudtts.parameter.AudioEncoding
import darren.googlecloudtts.parameter.VoiceSelectionParams
import dev.abhishekkumar.canvasview.CanvasView
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream

class HurufLevelTigaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOAL = "extra_soal"
    }

    private lateinit var binding: ActivityHurufLevelTigaBinding
    private val viewModel: PredictHurufViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufLevelTigaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        binding.levelTigaHurufBack.setOnClickListener {
            onBackPressed()
        }

        val data = intent.getParcelableExtra<Soal>(EXTRA_SOAL) as Soal

        show(data)
        draw()

        binding.spkTigaHuruf.setOnClickListener {
            speak(data.keterangan)
//            binding.scrollTigaAngka.setOnTouchListener(OnTouchListener { v, event -> true })
        }
    }

    private fun show(data: Soal){
        binding.soalHurufDipilih.text = data.keterangan
        binding.levelHurufKe.text = "Level ke ${data.id_level}"
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

        binding.refreshTigaHuruf.setOnClickListener {
            binding.cnvsLevelTigaHurufone.clearCanvas()
            binding.cnvsLevelTigaHuruftwo.clearCanvas()
            binding.cnvsLevelTigaHurufthree.clearCanvas()
            binding.cnvsLevelTigaHuruffour.clearCanvas()
            binding.cnvsLevelTigaHuruffive.clearCanvas()
            binding.cnvsLevelTigaHurufsix.clearCanvas()
        }
    }
}