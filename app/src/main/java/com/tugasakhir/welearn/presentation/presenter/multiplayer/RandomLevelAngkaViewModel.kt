package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class RandomLevelAngkaViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun randomSoalAngkaByLevel(level: Int) =
        useCase.soalMultiplayerAngka(level)

}