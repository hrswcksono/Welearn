package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class PredictHurufMultiViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun predictHurufMulti(id_game: Int,id_soal: Int, duration: Int,token: String) =
        useCase.predictHurufMulti(id_game, id_soal, duration, token)

}