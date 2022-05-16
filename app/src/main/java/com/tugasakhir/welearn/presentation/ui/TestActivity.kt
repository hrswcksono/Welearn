package com.tugasakhir.welearn.presentation.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import android.util.Base64.encodeToString
import androidx.appcompat.app.AppCompatActivity
import com.tugasakhir.welearn.core.utils.Constants.Companion.GOOGLE_API_KEY
import com.tugasakhir.welearn.databinding.ActivityTestBinding
import darren.googlecloudtts.GoogleCloudTTSFactory
import darren.googlecloudtts.parameter.AudioConfig
import darren.googlecloudtts.parameter.AudioEncoding
import darren.googlecloudtts.parameter.VoiceSelectionParams
import java.io.ByteArrayOutputStream


class TestActivity : AppCompatActivity(){

    private lateinit var binding: ActivityTestBinding
//    val myCanvasView = MyCanvasView(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)

//        val myCanvasView = MyCanvasView(this).apply {
//            systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
//            contentDescription = getString(R.string.canvasContentDescription)
//        }

//        setContentView(myCanvasView)

//        val canvasView = findViewById<CanvasView>(R.id.canvasView)
//        canvasView.setColorBackground(R.color.purple_200)
//        canvasView.setColorMarker(R.color.black)
//        canvasView.setStrokeWidth(12f)
//
//        binding.btnBitmap.setOnClickListener {
//            Toast.makeText(this,encodeImage(canvasView.getBitmap()), Toast.LENGTH_SHORT).show()
//        }
//        print(encodeImage(c))

//        val canvasView = CanvasView(this)
//        binding.parentView.addView(canvasView)

        binding.btnTest.setOnClickListener {
            speak("input audio")
        }

    }

    private fun speak(string: String) {
        // Set the ApiKey and create GoogleCloudTTS.
        val googleCloudTTS = GoogleCloudTTSFactory.create(GOOGLE_API_KEY)

// Load google cloud VoicesList and select the languageCode and voiceName with index (0 ~ N).
//        val voicesList = googleCloudTTS.load()
//        val languageCode = voicesList.languageCodes[0]
////        val temp = voicesList.languageCodes
//        val voiceName = voicesList.getVoiceNames(languageCode)[0]

// Set languageCode and voiceName, Rate and pitch parameter.
        googleCloudTTS.setVoiceSelectionParams(VoiceSelectionParams( "id-ID", "id-ID-Standard-A"))
            .setAudioConfig(AudioConfig(AudioEncoding.MP3, 1f , 10f));

// start speak
        googleCloudTTS.start(string);
//
//// stop speak
//        googleCloudTTS.stop();
//
//// pause speak
//        googleCloudTTS.pause();
//
//// resume speak
//        googleCloudTTS.resume();

    }

    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return encodeToString(b, Base64.DEFAULT)
    }
}