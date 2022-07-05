package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class RandomIDSoalMultiPresenter(private val useCase: WelearnUseCase): ViewModel() {

    fun randomIDSoalMultiByLevel(jenis: Int, level: Int) =
        useCase.getIDSoalMultiplayer(jenis, level)

}