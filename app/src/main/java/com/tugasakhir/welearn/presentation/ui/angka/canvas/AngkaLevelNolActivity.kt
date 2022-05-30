package com.tugasakhir.welearn.presentation.ui.angka.canvas

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import android.widget.Toast
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelNolBinding
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.presentation.ui.angka.PredictAngkaViewModel
import darren.googlecloudtts.GoogleCloudTTSFactory
import darren.googlecloudtts.parameter.AudioConfig
import darren.googlecloudtts.parameter.AudioEncoding
import darren.googlecloudtts.parameter.VoiceSelectionParams
import dev.abhishekkumar.canvasview.CanvasView
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream

class AngkaLevelNolActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOAL = "extra_soal"
    }

    private lateinit var binding: ActivityAngkaLevelNolBinding
    private val viewModel: PredictAngkaViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelNolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val data = intent.getParcelableExtra<Soal>(EXTRA_SOAL) as Soal

        show(data)

        binding.levelNolAngkaBack.setOnClickListener {
            onBackPressed()
        }

        binding.spkNolAngka.setOnClickListener {
            speak(data.keterangan)
        }

        draw()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        var eBitmap: Bitmap? = null
        var extraBitmap: Bitmap? = null
    }

    private fun show(data: Soal){
        binding.soalAngkaDipilih.text = data.keterangan
        binding.levelAngkaKe.text = "Level ke ${data.id_level}"
        binding.tvSoal.text = data.soal
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

//    private fun draw() {
//        val canvasView = findViewById<CanvasView>(R.id.cnstrlyt)
//        canvasView.setColorBackground(R.color.white)
//        canvasView.setColorMarker(R.color.black)
//        canvasView.setStrokeWidth(12f)
//
//        binding.refreshNolAngka.setOnClickListener {
//            canvasView.clearView()
//        }
//
//        binding.submitNolAngka.setOnClickListener {
//            Toast.makeText(this, encodeImage(canvasView.getBitmap()), Toast.LENGTH_LONG).show()
//            print(encodeImage(canvasView.getBitmap()))
//        }
//    }

    private fun draw() {

        binding.refreshNolAngka.setOnClickListener {
            binding.cnvsLevelNolAngka.clearCanvas()
        }

        binding.submitNolAngka.setOnClickListener {
            Toast.makeText(this, encodeImage(binding.cnvsLevelNolAngka.getBitmap()), Toast.LENGTH_LONG).show()
//            print(encodeImage(binding.cnvsLevelNolAngka.getBitmap()))
        }
    }

}