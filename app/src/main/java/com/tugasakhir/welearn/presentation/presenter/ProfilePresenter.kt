package com.tugasakhir.welearn.presentation.presenter

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class ProfilePresenter(private val useCase: WelearnUseCase): ViewModel() {
    fun detailUser() = useCase.userDetail()
}