package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class PredictHurufMultiViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun predictHurufMulti(idGame: Int,idSoal: Int,image: ArrayList<String> , duration: Int) =
        useCase.predictHurufMulti(idGame, idSoal,image , duration)

}