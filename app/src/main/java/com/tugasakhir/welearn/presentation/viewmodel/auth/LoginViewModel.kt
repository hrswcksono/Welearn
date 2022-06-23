package com.tugasakhir.welearn.presentation.viewmodel.auth

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.model.Login
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class LoginViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun loginUser(username: String, password: String): Flow<Login> = useCase.userLogin(username, password)
}