package com.tugasakhir.welearn.presentation

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import androidx.appcompat.app.AppCompatActivity
import com.tugasakhir.welearn.utils.ExitApp
import com.tugasakhir.welearn.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivitySplashScreenBinding
import com.tugasakhir.welearn.presentation.view.auth.LoginActivity
import org.koin.android.ext.android.inject

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

        startService(Intent(baseContext, ExitApp::class.java))

        Handler(Looper.getMainLooper()).postDelayed({
            if (sessionManager.fetchAuthToken() == null){
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            } else {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            }
            finish()
        }, 3000)
    }
}