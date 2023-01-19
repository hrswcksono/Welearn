package com.tugasakhir.welearn.presentation.ui.splashScreen

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.MainActivity
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivitySplashScreenBinding
import com.tugasakhir.welearn.presentation.ui.auth.LoginActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        sessionManager = SharedPreference(this)

        if (Build.VERSION.SDK_INT < 16) {
            @Suppress("DEPRECATION")
            window.setFlags(
                FLAG_FULLSCREEN,
                FLAG_FULLSCREEN
            )
        }

        Handler(Looper.getMainLooper()).postDelayed({
            sessionManager.deleteToken()
            startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            finish()
        }, 3000)

        unsubscribeTopic()
    }

    private fun unsubscribeTopic(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC_GENERAL)
        FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC_JOIN_ANGKA)
        FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC_JOIN_HURUF)
    }
}