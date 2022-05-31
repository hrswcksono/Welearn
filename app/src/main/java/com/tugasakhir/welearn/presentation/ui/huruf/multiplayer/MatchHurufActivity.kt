package com.tugasakhir.welearn.presentation.ui.huruf.multiplayer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_GENERAL
import com.tugasakhir.welearn.core.utils.FirebaseService
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityMatchHurufBinding
import com.tugasakhir.welearn.domain.model.NotificationData
import com.tugasakhir.welearn.domain.model.PushNotification
import com.tugasakhir.welearn.presentation.ui.PushNotificationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class MatchHurufActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchHurufBinding
    private val viewModel: PushNotificationViewModel by viewModel()
    private val viewModelRandom: RandomLevelHurufViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchHurufBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        sessionManager = SharedPreference(this)

        binding.imageView4.setOnClickListener {
            onBackPressed()
        }

        binding.btnFindHuruf.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModel.pushNotification(
                        PushNotification(
                            NotificationData(
                                "Pertandingan MultiPlayer Segera Dimuali!"
                                ,"Siapa yang ingin ikut?"
                                , "huruf"
                            ),
                            TOPIC_GENERAL
                        )
                    ).collectLatest {  }
                }
            }
        }

        binding.btnHurufAcak.setOnClickListener {
            val inputLevel = binding.tfLevelHuruf.text.toString()
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModelRandom.randomSoalHurufByLevel(
                        inputLevel.toInt(),
                        sessionManager.fetchAuthToken().toString()
                    ).collectLatest {  }
                }
            }
        }

        binding.btnStartHuruf.setOnClickListener {

        }
    }
}