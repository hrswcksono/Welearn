package com.tugasakhir.welearn.presentation.ui.huruf.multiplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.MainActivity
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_GENERAL
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_JOIN_HURUF
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityHurufReadyBinding
import com.tugasakhir.welearn.domain.model.NotificationData
import com.tugasakhir.welearn.domain.model.PushNotification
import com.tugasakhir.welearn.presentation.presenter.multiplayer.PushNotificationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class HurufReadyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHurufReadyBinding
    private val viewModelGame: PushNotificationViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    companion object{
        const val ID_GAME = "id_game"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufReadyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        sessionManager = SharedPreference(this)

        binding.backToHome.setOnClickListener {
            startActivity(Intent(this@HurufReadyActivity, MainActivity::class.java))
        }

        ready()
    }

    private fun ready() {
        binding.btnHurufReady.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModelGame.pushNotification(
                    PushNotification(
                        NotificationData(
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