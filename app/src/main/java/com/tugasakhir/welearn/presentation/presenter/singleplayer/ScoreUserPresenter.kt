package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.singleplayer.SinglePlayerUseCase

class ScoreUserPresenter(private val useCase: SinglePlayerUseCase): ViewModel() {

    fun userScore(id: Int, authToken: String) = useCase.userScore(id, authToken)

}