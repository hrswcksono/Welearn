package com.tugasakhir.welearn.presentation.presenter.user

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import com.tugasakhir.welearn.domain.usecase.user.UserUseCase

class RegisterPresenter(private val useCase: UserUseCase): ViewModel() {

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