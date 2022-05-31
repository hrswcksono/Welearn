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
import com.tugasakhir.welearn.MainActivity
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityAngkaLevelNolBinding
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.presentation.ui.TestViewModel
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

class AngkaLevelNolActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOAL = "extra_soal"
    }

    private lateinit var binding: ActivityAngkaLevelNolBinding
    private val viewModel: PredictAngkaViewModel by viewModel()
    private val testViewModel: TestViewModel by viewModel()
    private val soalViewModel: SoalAngkaByIDViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaLevelNolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val idSoal = intent.getIntExtra(EXTRA_SOAL, 0)
        sessionManager = SharedPreference(this)

        binding.levelNolAngkaBack.setOnClickListener {
            onBackPressed()
        }

        Toast.makeText(this, sessionManager.fetchAuthToken().toString(), Toast.LENGTH_SHORT).show()

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                soalViewModel.soalAngkaByID(idSoal, sessionManager.fetchAuthToken().toString()).collectLatest {
                    show(it)
                }
            }
        }

        draw()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        var eBitmap: Bitmap? = null
        var extraBitmap: Bitmap? = null
    }

    private fun show(data: Soal){
        binding.spkNolAngka.setOnClickListener {
            speak(data.keterangan)
        }
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

    private fun draw() {
        sessionManager = SharedPreference(this)

        binding.refreshNolAngka.setOnClickListener {
            binding.cnvsLevelNolAngka.clearCanvas()
        }

        binding.submitNolAngka.setOnClickListener {
//            Toast.makeText(this, encodeImage(binding.cnvsLevelNolAngka.getBitmap()), Toast.LENGTH_LONG).show()
//            print(encodeImage(binding.cnvsLevelNolAngka.getBitmap()))
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main){
                    testViewModel.testPredict("1", sessionManager.fetchAuthToken().toString()).collectLatest {
                        if (it.message == "WIN") {
                            alert(it.message, it.text)
                        } else {
                            alert(it.message, it.text)
                        }
                    }
                }
            }
        }
    }

    private fun alert(string: String, body: String){
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText(string)
            .setContentText(body)
            .setConfirmText("Lihat Skor")
            .setConfirmClickListener {
                    sDialog -> sDialog.dismissWithAnimation()
                startActivity(Intent(this, MainActivity::class.java))
            }
            .show()
    }

}