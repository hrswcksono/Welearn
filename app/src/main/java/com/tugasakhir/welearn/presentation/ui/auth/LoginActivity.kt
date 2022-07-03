package com.tugasakhir.welearn.presentation.ui.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.tugasakhir.welearn.MainActivity
import com.tugasakhir.welearn.core.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.core.utils.CustomDialogBox
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityLoginBinding
import com.tugasakhir.welearn.domain.model.Login
import com.tugasakhir.welearn.presentation.presenter.auth.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        RemoteDataSource.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

//        binding.progressBar2.progressBackgroundTintBlendMode
        binding.progressLogin.visibility = View.INVISIBLE

        sessionManager = SharedPreference(this)

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.tvReg.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser(){
        val username = binding.usernameLogin.editableText
        val password = binding.passwordLogin.editableText

//        binding.progressBar2.visibility = View.VISIBLE
        binding.progressLogin.visibility = View.VISIBLE

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.loginUser(username.toString(), password.toString()).collectLatest {
//                    CustomDialogBox.onlyTitle(
//                        this@LoginActivity,
//                        SweetAlertDialog.SUCCESS_TYPE,
//                        "Berhasil login"
//                    ) {
////                        onBackPressed()
//                        login(it)
//                    }
//                    binding.progressBar2.visibility = View.INVISIBLE
                    CustomDialogBox.notifOnly(this@LoginActivity, "Berhasil Login")
                    login(it)
                }
            }
        }
    }

    private fun login(login: Login) {

//        sessionManager.saveAuthToken(login.token)
        sessionManager.saveName(login.name)
        sessionManager.saveUserID(login.id)
        RemoteDataSource.tokenUser = login.token
        if (login.token.isNotEmpty()){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
    }

}