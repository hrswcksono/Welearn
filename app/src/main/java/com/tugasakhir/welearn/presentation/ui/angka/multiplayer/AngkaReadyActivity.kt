package com.tugasakhir.welearn.presentation.ui.angka.multiplayer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_GENERAL
import com.tugasakhir.welearn.core.utils.Constants.Companion.TOPIC_JOIN_ANGKA
import com.tugasakhir.welearn.databinding.ActivityAngkaReadyBinding
import com.tugasakhir.welearn.presentation.ui.PushNotificationViewModel
import com.tugasakhir.welearn.presentation.ui.angka.canvas.SoalAngkaByIDViewModel
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

        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_GENERAL)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_JOIN_ANGKA)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_JOIN_ANGKA).addOnSuccessListener {
            Toast.makeText(
                applicationContext,
                "Success",
                Toast.LENGTH_LONG
            ).show()
        }

        binding.btnAngkaReady.setOnClickListener {
            Handler(Looper.getMainLooper()).postDelayed({

            }, 1000)
        }
    }

    override fun onBackPressed() {
        return
    }
}