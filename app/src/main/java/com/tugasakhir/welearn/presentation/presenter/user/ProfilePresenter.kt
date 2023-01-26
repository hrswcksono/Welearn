package com.tugasakhir.welearn.presentation.presenter.user

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.auth.AuthUseCase

class ProfilePresenter(private val useCase: AuthUseCase): ViewModel() {
    fun detailUser(authToken: String) = useCase.userDetail(authToken)
}