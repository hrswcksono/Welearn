package com.tugasakhir.welearn.presentation.ui.angka.multiplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.databinding.ActivityAngkaReadyBinding
import com.tugasakhir.welearn.presentation.ui.PushNotificationViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AngkaReadyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAngkaReadyBinding
    private val viewModel: PushNotificationViewModel by viewModel()
    private val viewModelSoal: SoalAngkaByIDViewModel by viewModel()

    companion object{
        const val LEVEL_ANGKA = "level_angka"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngkaReadyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnAngkaReady.setOnClickListener {
            Handler(Looper.getMainLooper()).postDelayed({
                FirebaseMessaging.getInstance().subscribeToTopic("/topics/myTopics")
            }, 1000)
        }
    }
}