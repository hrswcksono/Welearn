package com.tugasakhir.welearn.presentation.presenter.user

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import com.tugasakhir.welearn.domain.usecase.user.UserUseCase

class LoginPresenter(private val useCase: UserUseCase): ViewModel() {

    fun loginUser(username: String, password: String) =
        useCase.userLogin(username, password)

}