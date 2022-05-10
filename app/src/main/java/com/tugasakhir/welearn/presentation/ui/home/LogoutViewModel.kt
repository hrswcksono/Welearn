package com.tugasakhir.welearn.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class LogoutViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun logoutUser(token: String): Flow<String> = useCase.userLogout(token)

}