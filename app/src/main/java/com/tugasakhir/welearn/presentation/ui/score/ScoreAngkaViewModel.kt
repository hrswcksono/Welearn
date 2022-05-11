package com.tugasakhir.welearn.presentation.ui.score

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.model.UserScore
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class ScoreAngkaViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun highScoreAngka(token: String): Flow<List<UserScore>> =
        useCase.angkaHighScore(token)
}