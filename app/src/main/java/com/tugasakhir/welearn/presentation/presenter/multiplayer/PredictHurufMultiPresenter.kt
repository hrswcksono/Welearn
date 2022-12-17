package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class PredictHurufMultiPresenter(private val useCase: WelearnUseCase): ViewModel() {

    fun predictHurufMulti(idGame: Int,idSoal: Int,score: Int , duration: Int) =
        useCase.predictHurufMulti(idGame, idSoal,score , duration)

}