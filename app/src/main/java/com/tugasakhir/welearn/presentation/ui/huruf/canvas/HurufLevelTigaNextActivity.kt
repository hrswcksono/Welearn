package com.tugasakhir.welearn.presentation.ui.huruf.canvas

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.databinding.ActivityHurufLevelTigaNextBinding
import com.tugasakhir.welearn.domain.model.Soal
import darren.googlecloudtts.GoogleCloudTTSFactory
import darren.googlecloudtts.parameter.AudioConfig
import darren.googlecloudtts.parameter.AudioEncoding
import darren.googlecloudtts.parameter.VoiceSelectionParams
import java.io.ByteArrayOutputStream

class HurufLevelTigaNextActivity : AppCompatActivity() {

    companion object {
        const val DATA_DRAWING = "data_drawing"
        const val EXTRA_DATA_SOAL = "extra_data_soal"
    }

    private lateinit var binding: ActivityHurufLevelTigaNextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufLevelTigaNextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        supportActionBar?.hide()

        val dataSoal = intent.getParcelableExtra<Soal>(EXTRA_DATA_SOAL) as Soal
        var dataDraw = intent.getStringArrayListExtra(DATA_DRAWING)

        draw()
        show(dataSoal)

    }

    private fun show(data: Soal) {
        binding.spkTigaNextHuruf.setOnClickListener {
            speak(data.keterangan)
        }
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
        binding.refreshTigaNextHuruf.setOnClickListener {
            binding.cnvsLevelTigaHurufseven.clearCanvas()
            binding.cnvsLevelTigaHurufeight.clearCanvas()
            binding.cnvsLevelTigaHurufnine.clearCanvas()
        }
    }

    override fun onBackPressed() {
        return
    }
}