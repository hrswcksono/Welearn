package com.tugasakhir.welearn.presentation.ui.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.tugasakhir.welearn.MainActivity
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivitySplashScreenBinding
import com.tugasakhir.welearn.presentation.ui.TestActivity
import com.tugasakhir.welearn.presentation.ui.auth.login.LoginActivity
import com.tugasakhir.welearn.presentation.ui.score.ScoreActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        sessionManager = SharedPreference(this)

        Handler(Looper.getMainLooper()).postDelayed({
            sessionManager.deleteToken()
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }, 3000)
    }
}