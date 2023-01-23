package com.tugasakhir.welearn.presentation.presenter.user

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import com.tugasakhir.welearn.domain.usecase.user.UserUseCase

class LogoutPresenter(private val useCase: UserUseCase): ViewModel() {

    fun logoutUser() = useCase.userLogout()

}