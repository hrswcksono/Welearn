package com.tugasakhir.welearn.presentation.ui.huruf.multiplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_GENERAL
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_JOIN_HURUF
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityHurufReadyBinding
import com.tugasakhir.welearn.domain.model.PushNotificationStart
import com.tugasakhir.welearn.domain.model.StartGame
import com.tugasakhir.welearn.presentation.ui.multiplayer.viewmodel.PushNotificationStartViewModel
import com.tugasakhir.welearn.presentation.ui.multiplayer.viewmodel.PushNotificationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class HurufReadyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHurufReadyBinding
    private val viewModel: PushNotificationViewModel by viewModel()
    private val viewModelSoal: SoalHurufByIDViewModel by viewModel()
    private val viewModelGame: PushNotificationStartViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    companion object{
        const val LEVEL_HURUF = "level_huruf"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufReadyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        sessionManager = SharedPreference(this)

        ready()
    }

    private fun ready() {
        binding.btnHurufReady.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModelGame.pushNotification(
                        PushNotificationStart(
                            StartGame(
                                "Perhatian...!",
                                "${sessionManager.fetchName()} telah bergabung!",
                                "",
                                "0",
                                0,
                                "gabung_huruf"
                            ),
                            TOPIC_JOIN_HURUF,
                            "high"
                        )
                    ).collectLatest {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_GENERAL)
                        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_JOIN_HURUF)
                        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_JOIN_HURUF).addOnSuccessListener {
//                            dialogBox()
                        }
                    }
                }
            }
//            Handler(Looper.getMainLooper()).postDelayed({
//            }, 1000)
        }
    }

    override fun onBackPressed() {
        return
    }

}