package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import com.tugasakhir.welearn.domain.usecase.score.ScoreUseCase
import com.tugasakhir.welearn.domain.usecase.singleplayer.SinglePlayerUseCase

class LevelSoalPresenter(private val useCase: SinglePlayerUseCase): ViewModel() {

    fun getLevelSoal(idLevel: Int) = useCase.getLevel(idLevel)
}