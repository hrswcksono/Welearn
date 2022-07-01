package com.tugasakhir.welearn.presentation.presenter.auth

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class LogoutViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun logoutUser(): Flow<String> = useCase.userLogout()

}