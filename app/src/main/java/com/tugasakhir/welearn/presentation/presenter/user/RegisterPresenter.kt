package com.tugasakhir.welearn.presentation.presenter.user

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.auth.AuthUseCase

class RegisterPresenter(private val useCase: AuthUseCase): ViewModel() {

    fun registerUser(
        username: String,
        password: String,
        email: String,
        name: String,
        jenisKelamin: String)
    = useCase.userRegister(
        username, password, email, name, jenisKelamin
    )

}