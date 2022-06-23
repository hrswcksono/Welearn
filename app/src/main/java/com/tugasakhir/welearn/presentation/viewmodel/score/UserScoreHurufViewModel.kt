package com.tugasakhir.welearn.presentation.viewmodel.score

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class UserScoreHurufViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun userScoreHuruf(token: String) = useCase.userHurufScore(token)

}