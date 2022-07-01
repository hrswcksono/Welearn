package com.tugasakhir.welearn.presentation.presenter

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.model.User
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class ProfileViewModel(private val useCase: WelearnUseCase): ViewModel() {
    fun detailUser(): Flow<User> = useCase.userDetail()
}