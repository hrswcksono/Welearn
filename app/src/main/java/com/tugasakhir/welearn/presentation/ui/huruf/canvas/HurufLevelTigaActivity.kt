package com.tugasakhir.welearn.presentation.ui.huruf.canvas

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.databinding.ActivityHurufLevelTigaBinding
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.presentation.ui.angka.canvas.AngkaLevelNolActivity
import darren.googlecloudtts.GoogleCloudTTSFactory
import darren.googlecloudtts.parameter.AudioConfig
import darren.googlecloudtts.parameter.AudioEncoding
import darren.googlecloudtts.parameter.VoiceSelectionParams
import dev.abhishekkumar.canvasview.CanvasView
import java.io.ByteArrayOutputStream

class HurufLevelTigaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOAL = "extra_soal"
    }

    private lateinit var binding: ActivityHurufLevelTigaBinding

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
        }
    }

    private fun draw() {
        drawOne()
        drawTwo()
        drawThree()
        drawFour()
        drawFive()
        drawSix()
        drawSeven()
        drawEight()
        drawNine()
    }

    private fun show(data: Soal){
        binding.soalHurufDipilih.setText(data.keterangan)
        binding.levelHurufKe.setText("Level ke ${data.id_level}")
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

    private fun drawOne(): String?{
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHurufone)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
        return encodeImage(canvasView.getBitmap())
    }

    private fun drawTwo(): String?{
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHuruftwo)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
        return encodeImage(canvasView.getBitmap())
    }

    private fun drawThree(): String?{
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHurufthree)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
        return encodeImage(canvasView.getBitmap())
    }

    private fun drawFour(): String?{
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHuruffour)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
        return encodeImage(canvasView.getBitmap())
    }

    private fun drawFive(): String?{
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHuruffive)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
        return encodeImage(canvasView.getBitmap())
    }

    private fun drawSix(): String?{
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHurufsix)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
        return encodeImage(canvasView.getBitmap())
    }

    private fun drawSeven(): String?{
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHurufseven)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
        return encodeImage(canvasView.getBitmap())
    }

    private fun drawEight(): String?{
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHurufeight)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
        return encodeImage(canvasView.getBitmap())
    }

    private fun drawNine(): String?{
        val canvasView = findViewById<CanvasView>(R.id.cnvsLevelTigaHurufnine)
        canvasView.setColorBackground(R.color.white)
        canvasView.setColorMarker(R.color.black)
        canvasView.setStrokeWidth(12f)
        return encodeImage(canvasView.getBitmap())
    }
}