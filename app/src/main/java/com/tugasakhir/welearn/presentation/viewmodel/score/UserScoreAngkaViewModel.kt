package com.tugasakhir.welearn.presentation.viewmodel.score

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class UserScoreAngkaViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun userScoreAngka(token: String) = useCase.userAngkaScore(token)

}