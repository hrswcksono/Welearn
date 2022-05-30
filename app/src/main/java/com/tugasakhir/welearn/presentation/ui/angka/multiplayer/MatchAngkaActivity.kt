package com.tugasakhir.welearn.presentation.ui.angka.multiplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.TOPIC
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityMatchAngkaBinding
import com.tugasakhir.welearn.domain.model.*
import com.tugasakhir.welearn.presentation.ui.PushNotificationStartViewModel
import com.tugasakhir.welearn.presentation.ui.PushNotificationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel


const val TOPIC_ANGKA = "/topics/angka"
const val START_ANGKA = "/topics/start_angka"
class MatchAngkaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchAngkaBinding
    private val viewModel: PushNotificationViewModel by viewModel()
    private val viewModelRandom: RandomLevelAngkaViewModel by viewModel()
    private val viewModelStartGame: PushNotificationStartViewModel by viewModel()
    private lateinit var soal: Soal

    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchAngkaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        sessionManager = SharedPreference(this)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        binding.btnAngkaAcak.setOnClickListener {
            val inputLevel = binding.tfLevelAngka.text.toString()
            if (inputLevel.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.Default) {
                    withContext(Dispatchers.Main) {
                        viewModelRandom.randomSoalAngkaByLevel(
                            inputLevel.toInt(),
                            sessionManager.fetchAuthToken().toString()
                        ).collectLatest {
                            if (it.id_soal.toString().isNotEmpty()){
                                dialogBox()
                                soal = it
                            }
                        }
                    }
                }
            }
        }

        binding.btnFindAngka.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModel.pushNotification(
                        PushNotification(
                            NotificationData(
                                "Pertandingan MultiPlayer Segera Dimuali!"
                                ,"Siapa yang ingin ikut?"
                                ,"angka"
                            ),
                            TOPIC
                        )
                    ).collectLatest {  }
                }
            }
        }

        binding.btnStartAngka.setOnClickListener{
            if (soal.id_soal != null) {
                lifecycleScope.launch(Dispatchers.Default) {
                    withContext(Dispatchers.Main) {
                        viewModelStartGame.pushNotification(
                            PushNotificationStart(
                                StartGame(
                                    "startangka",
                                    soal.id_soal.toString(),
                                    soal.id_jenis_soal.toString(),
                                    soal.id_level.toString(),
                                    soal.soal,
                                    soal.keterangan,
                                    soal.jawaban
                                ),
                                START_ANGKA
                            )
                        )
                    }
                }
            }
        }
    }

    private fun dialogBox() {
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Berhasil")
            .setContentText("Mendapatkan Soal Angka")
            .show()
    }
}