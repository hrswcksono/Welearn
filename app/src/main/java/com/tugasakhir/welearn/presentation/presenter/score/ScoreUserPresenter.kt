package com.tugasakhir.welearn.presentation.presenter.score

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class ScoreUserPresenter(private val useCase: WelearnUseCase): ViewModel() {

    fun userScore(id: Int) = useCase.userScore(id)

}