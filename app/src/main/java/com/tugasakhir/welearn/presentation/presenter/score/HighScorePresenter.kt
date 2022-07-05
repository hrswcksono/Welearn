package com.tugasakhir.welearn.presentation.presenter.score

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class HighScorePresenter(private val useCase: WelearnUseCase): ViewModel() {

    fun highScore(id: Int) = useCase.getHighScore(id)
}