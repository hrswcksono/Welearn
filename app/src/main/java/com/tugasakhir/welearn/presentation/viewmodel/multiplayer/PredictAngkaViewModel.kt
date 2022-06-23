package com.tugasakhir.welearn.presentation.viewmodel.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class PredictAngkaViewModel(private val useCase: WelearnUseCase): ViewModel() {
    fun predictAngkaMulti(id_game: Int, id_jenis: Int, image: ArrayList<String> ,token: String) =
        useCase.predictAngkaMulti(id_game, id_jenis, image, token)
}