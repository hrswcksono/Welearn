package com.tugasakhir.welearn.presentation.presenter.user

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.auth.AuthUseCase

class LogoutPresenter(private val useCase: AuthUseCase): ViewModel() {
    fun logoutUser() = useCase.userLogout()
}