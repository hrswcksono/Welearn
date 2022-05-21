package com.tugasakhir.welearn.presentation.ui.angka.canvas

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelEmpatBinding
import com.tugasakhir.welearn.domain.model.Soal
import darren.googlecloudtts.GoogleCloudTTSFactory
import darren.googlecloudtts.parameter.AudioConfig
import darren.googlecloudtts.parameter.AudioEncoding
import darren.googlecloudtts.parameter.VoiceSelectionParams
import dev.abhishekkumar.canvasview.CanvasView
import java.io.ByteArrayOutputStream

class AngkaLevelEmpatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAngkaLevelEmpatBinding

    companion object {
        const val EXTRA_SOAL = "extra_soal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelEmpatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val data = intent.getParcelableExtra<Soal>(EXTRA_SOAL) as Soal

        show(data)

        binding.spkEmpatAngka.setOnClickListener {
            speak(data.keterangan)
        }

        draw()
    }

    private fun show(data: Soal){
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
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelEmpatAngkaOne)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)

        val canvasView1 = findViewById<CanvasView>(R.id.cnvsLevelEmpatAngkaTwo)
        canvasView1.setColorBackground(R.color.white)
        canvasView1.setColorMarker(R.color.black)
        canvasView1.setStrokeWidth(12f)

        binding.submitEmpatAngka.setOnClickListener {

        }

        binding.refreshEmpatAngka.setOnClickListener {
            canvasView.clearView()
            canvasView1.clearView()
        }

    }

}