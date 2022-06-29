package com.tugasakhir.welearn.presentation.presenter.score

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class ScoreMultiViewModel(private val useCase: WelearnUseCase): ViewModel()  {

    fun scoreMulti(id_game: Int, token: String) =
        useCase.scoreMulti(id_game, token)
}