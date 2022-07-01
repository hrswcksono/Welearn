package com.tugasakhir.welearn.presentation.presenter.score

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class UserScoreAngkaViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun userScoreAngka() = useCase.userAngkaScore()

}