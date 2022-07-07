package com.tugasakhir.welearn.presentation.presenter.auth

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class LoginPresenter(private val useCase: WelearnUseCase): ViewModel() {

    fun loginUser(username: String, password: String) =
        useCase.userLogin(username, password)

}