package com.tugasakhir.welearn.presentation.presenter.score

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.model.UserScore
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class ScoreHurufViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun highScoreHuruf() = useCase.hurufHighScore()
}