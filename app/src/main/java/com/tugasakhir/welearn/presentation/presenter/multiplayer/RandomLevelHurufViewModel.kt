package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class RandomLevelHurufViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun randomSoalHurufByLevel(level: Int, token: String) =
        useCase.soalMultiplayerHuruf(level, token)

}