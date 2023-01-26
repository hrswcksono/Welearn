package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.singleplayer.SinglePlayerUseCase

class HighScorePresenter(private val useCase: SinglePlayerUseCase): ViewModel() {
    fun highScore(id: Int, authToken: String) = useCase.getHighScore(id, authToken)
}