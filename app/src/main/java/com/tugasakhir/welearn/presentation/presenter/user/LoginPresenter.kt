package com.tugasakhir.welearn.presentation.presenter.user

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.auth.AuthUseCase

class LoginPresenter(private val useCase: AuthUseCase): ViewModel() {
    fun loginUser(username: String, password: String) =
        useCase.userLogin(username, password)
}