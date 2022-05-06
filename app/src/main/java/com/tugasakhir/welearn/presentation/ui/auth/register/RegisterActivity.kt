package com.tugasakhir.welearn.presentation.ui.auth.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.welearn.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }

    private fun register(){
        val name = binding.nameRegister.editableText
        val username = binding.usernameRegister.editableText
        val email = binding.emailRegister.editableText
        val jenis_kelamin = binding.jenisKelaminRegister.editableText
        val password = binding.passwordRegister.editableText
        val retype_password = binding.retypePasswordRegister.editableText
    }
}