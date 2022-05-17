package com.tugasakhir.welearn.presentation.ui.angka.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class SoalAngkaByIDViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun soalAngkaByID(id: Int, token: String): Flow<Soal> =
        useCase.getSoalAngkaByID(id, token)

}