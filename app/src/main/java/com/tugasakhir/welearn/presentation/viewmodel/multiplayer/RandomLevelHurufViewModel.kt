package com.tugasakhir.welearn.presentation.viewmodel.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class RandomLevelHurufViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun randomSoalHurufByLevel(level: Int, token: String) =
        useCase.soalMultiplayerHuruf(level, token)

}