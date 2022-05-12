package com.tugasakhir.welearn.presentation.ui.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.tugasakhir.welearn.MainActivity
import com.tugasakhir.welearn.databinding.ActivitySplashScreenBinding
import com.tugasakhir.welearn.presentation.ui.auth.login.LoginActivity
import com.tugasakhir.welearn.presentation.ui.score.ScoreActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            finish()
        }, 3000)

    }
}