package com.tugasakhir.welearn.presentation.ui.huruf.canvas

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.StrictMode
import android.util.Base64
import android.view.View.OnTouchListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityHurufLevelTigaBinding
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.presentation.ui.angka.canvas.AngkaLevelNolActivity
import com.tugasakhir.welearn.presentation.ui.huruf.PredictHurufViewModel
import com.tugasakhir.welearn.presentation.ui.huruf.canvas.HurufLevelTigaNextActivity.Companion.DATA_DRAWING
import com.tugasakhir.welearn.presentation.ui.huruf.canvas.HurufLevelTigaNextActivity.Companion.EXTRA_DATA_SOAL
import com.tugasakhir.welearn.presentation.ui.huruf.multiplayer.SoalHurufByIDViewModel
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

class HurufLevelTigaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOAL = "extra_soal"
    }

    private lateinit var binding: ActivityHurufLevelTigaBinding
    private val viewModel: PredictHurufViewModel by viewModel()
    private val soalViewModel: SoalHurufByIDViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufLevelTigaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val idSoal = intent.getIntExtra(EXTRA_SOAL, 0)
        sessionManager = SharedPreference(this)

        binding.levelTigaHurufBack.setOnClickListener {
            onBackPressed()
        }

        draw()

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.soalHurufByID(idSoal, sessionManager.fetchAuthToken().toString()).collectLatest {
                    show(it)
                }
            }
        }

    }

    private fun show(data: Soal){
        binding.spkTigaHuruf.setOnClickListener {
            speak(data.keterangan)
        }
        binding.soalHurufDipilih.text = data.keterangan
        binding.levelHurufKe.text = "Level ke ${data.id_level}"

        binding.nextTigaHuruf.setOnClickListener {
            val moveNextActivity =  Intent(this@HurufLevelTigaActivity, HurufLevelTigaNextActivity::class.java)
            moveNextActivity.putStringArrayListExtra(DATA_DRAWING, passDataDraw())
            moveNextActivity.putExtra(EXTRA_DATA_SOAL, data)
            startActivity(moveNextActivity)
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

    private fun encodeImage(bm: Bitmap): String {
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

    private fun passDataDraw() : ArrayList<String> {
        val drawList = ArrayList<String>()
        drawList.add(encodeImage(binding.cnvsLevelTigaHurufone.getBitmap()))
        drawList.add(encodeImage(binding.cnvsLevelTigaHuruftwo.getBitmap()))
        drawList.add(encodeImage(binding.cnvsLevelTigaHurufthree.getBitmap()))
        drawList.add(encodeImage(binding.cnvsLevelTigaHuruffour.getBitmap()))
        drawList.add(encodeImage(binding.cnvsLevelTigaHuruffive.getBitmap()))
        drawList.add(encodeImage(binding.cnvsLevelTigaHurufsix.getBitmap()))
        return drawList
    }
}