package com.tugasakhir.welearn.presentation.ui.huruf.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class RandomLevelHurufViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun randomSoalHurufByLevel(level: Int, token: String): Flow<Soal> =
        useCase.getSoalHurufByID(level, token)

}