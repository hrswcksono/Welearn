package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase

class RandomIDSoalMultiPresenter(private val useCase: MultiPlayerUseCase): ViewModel() {
    fun randomIDSoalMultiByLevel(jenis: Int, level: Int, authToken: String) =
        useCase.getIDSoalMultiplayer(jenis, level, authToken)
}