package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class PredictAngkaMultiViewModel (private val useCase: WelearnUseCase): ViewModel() {

    fun predictAngkaMulti(id_game: Int,id_soal: Int,image: ArrayList<String> , duration: Int) =
        useCase.predictAngkaMulti(id_game, id_soal,image , duration)

}