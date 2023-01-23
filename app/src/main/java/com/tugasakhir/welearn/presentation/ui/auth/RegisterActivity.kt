package com.tugasakhir.welearn.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cn.pedant.SweetAlert.SweetAlertDialog
<<<<<<< HEAD
import com.google.firebase.messaging.FirebaseMessaging
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.core.utils.Constants
=======
>>>>>>> da79e37b7ac3186a7049a99dd22654ee1eeb405d
import com.tugasakhir.welearn.databinding.ActivityRegisterBinding
import com.tugasakhir.welearn.presentation.presenter.user.RegisterPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterPresenter by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.progressbarRegister.visibility = View.INVISIBLE

        binding.btnRegister.setOnClickListener {
            binding.progressbarRegister.visibility = View.VISIBLE
            register()
        }

        binding.btnregLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            this.finish()
        }
    }

    private fun register(){
        val name = binding.nameRegister.editableText
        val username = binding.usernameRegister.editableText
        val email = binding.emailRegister.editableText
        val password = binding.passwordRegister.editableText
        val retypePassword = binding.retypePasswordRegister.editableText
        val jenisKelamin = binding.jenisKelaminRegister.editableText

        if (password.toString() == retypePassword.toString()){
            lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    viewModel.registerUser(
                        username.toString(),
                        password.toString(),
                        email.toString() ,
                        name.toString(),
                        jenisKelamin.toString()).collectLatest {
                            if (it == "Successful: Created") {
                                loginSuccess()

                            }
                    }
                }
            }
        }else{
            Toast.makeText(this, "Password tidak sesuai", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginSuccess(){
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Yee...Berhasil terdaftar")
            .setConfirmClickListener {
                sDialog -> sDialog.dismissWithAnimation()
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }
            .show()
    }

}