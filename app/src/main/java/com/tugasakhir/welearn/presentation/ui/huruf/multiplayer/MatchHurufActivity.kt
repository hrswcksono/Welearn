package com.tugasakhir.welearn.presentation.ui.huruf.multiplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_GENERAL
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_JOIN_HURUF
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityMatchHurufBinding
import com.tugasakhir.welearn.domain.model.NotificationData
import com.tugasakhir.welearn.domain.model.PushNotification
import com.tugasakhir.welearn.domain.model.PushNotificationStart
import com.tugasakhir.welearn.domain.model.StartGame
import com.tugasakhir.welearn.presentation.presenter.multiplayer.MakeRoomViewModel
import com.tugasakhir.welearn.presentation.presenter.multiplayer.PushNotificationStartViewModel
import com.tugasakhir.welearn.presentation.presenter.multiplayer.PushNotificationViewModel
import com.tugasakhir.welearn.presentation.presenter.multiplayer.RandomLevelHurufViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class MatchHurufActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchHurufBinding
    private val viewModel: PushNotificationViewModel by viewModel()
    private val viewModelRandom: RandomLevelHurufViewModel by viewModel()
    private val viewModelGame: PushNotificationStartViewModel by viewModel()
    private val makeRoomViewModel: MakeRoomViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchHurufBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.imageView4.setOnClickListener {
            onBackPressed()
        }

        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_GENERAL)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_JOIN_HURUF)

        binding.btnHurufAcak.setOnClickListener {
            val inputLevel = binding.tfLevelHuruf.text.toString()
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModelRandom.randomSoalHurufByLevel(
                        inputLevel.toInt()
                    ).collectLatest {
                        if (it.isNotEmpty()){
                            startMatch(it, inputLevel.toInt())
                            findPlayer(it)
                        }
                    }
                }
            }
        }
    }

    private fun findPlayer(level: String) {
        binding.btnFindHuruf.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModel.pushNotification(
                        PushNotification(
                            NotificationData(
                                "sessionManager.fetchName().toString()} mengajak anda bertanding Angka level $level!"
                                ,"Siapa yang ingin ikut?"
                                ,"huruf",
                                ""
                            ),
                            TOPIC_GENERAL,
                            "high"
                        )
                    ).collectLatest {  }
                }
            }
        }
    }

    private fun startMatch(idSoal: String, idLevel: Int) {
        binding.btnStartHuruf.setOnClickListener{
            makeRoom(idSoal, idLevel)
        }
    }

    private fun makeRoom(idSoal: String, idLevel: Int){
        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Default) {
                makeRoomViewModel.makeRoom(1).collectLatest {
                    lifecycleScope.launch(Dispatchers.Default) {
                        withContext(Dispatchers.Main) {
                            viewModelGame.pushNotification(
                                PushNotificationStart(
                                    StartGame(
                                        "Pertandingan telah dimulai",
                                        "Selamat mengerjakan !",
                                        "starthuruf",
                                        idSoal,
                                        idLevel,
                                        it
                                    ),
                                    TOPIC_JOIN_HURUF,
                                    "high"
                                )
                            ).collectLatest {  }
                        }
                    }
                }
            }
        }
    }
}