package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class PredictAngkaMultiPresenter (private val useCase: WelearnUseCase): ViewModel() {

    fun predictAngkaMulti(idGame: Int,idSoal: Int,image: ArrayList<String> , duration: Int) =
        useCase.predictAngkaMulti(idGame, idSoal,image , duration)

}