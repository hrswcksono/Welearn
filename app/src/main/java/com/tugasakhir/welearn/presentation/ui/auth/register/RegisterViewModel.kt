package com.tugasakhir.welearn.presentation.ui.auth.register

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class RegisterViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun registerUser(username: String, password: String, email: String, name: String, jenis_kelamin: String): Flow<String> =
        useCase.userRegister(username, password, email, name, jenis_kelamin)
}