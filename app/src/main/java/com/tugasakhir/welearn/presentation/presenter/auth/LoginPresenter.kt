package com.tugasakhir.welearn.presentation.presenter.auth

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.model.Login
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class LoginPresenter(private val useCase: WelearnUseCase): ViewModel() {

    fun loginUser(username: String, password: String): Flow<Login> = useCase.userLogin(username, password)
}