package com.tugasakhir.welearn.presentation.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.tugasakhir.welearn.MainActivity
import com.tugasakhir.welearn.core.utils.CustomDialogBox
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.databinding.ActivityLoginBinding
import com.tugasakhir.welearn.domain.entity.LoginEntity
import com.tugasakhir.welearn.presentation.presenter.auth.LoginPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginPresenter by viewModel()
    private lateinit var sessionManager: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

//        RemoteDataSource.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

//        binding.progressBar2.progressBackgroundTintBlendMode
        binding.progressLogin.visibility = View.INVISIBLE

        sessionManager = SharedPreference(this)

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.btnToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

//        binding.cbPwd.setOnClickListener {
//            if(binding.cbPwd.isChecked) {
//                binding.passwordLogin.transformationMethod = HideReturnsTransformationMethod.getInstance()
//            }else{
//                binding.passwordLogin.transformationMethod = PasswordTransformationMethod.getInstance()
//            }
//        }
    }

    private fun loginUser(){
        val username = binding.usernameLogin.editableText
        val password = binding.passwordLogin.editableText

//        binding.progressBar2.visibility = View.VISIBLE
        binding.progressLogin.visibility = View.VISIBLE

        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.loginUser(username.toString(), password.toString()).collectLatest {
                    CustomDialogBox.notifOnly(this@LoginActivity, "Berhasil LoginEntity")
                    login(it)
                }
            }
        }
    }

    private fun login(login: LoginEntity) {

//        sessionManager.saveAuthToken(login.token)
        sessionManager.saveName(login.name)
        sessionManager.saveUserID(login.id)
//        RemoteDataSource.tokenUser = login.token
        if (login.token.isNotEmpty()){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
    }

    override fun onBackPressed() {
        return
    }

}