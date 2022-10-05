package com.tugasakhir.welearn.presentation.presenter.auth

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class RegisterPresenter(private val useCase: WelearnUseCase): ViewModel() {

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