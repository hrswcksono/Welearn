package com.tugasakhir.welearn.presentation.presenter.score

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class JoinedUserViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun getJoinedGame() = useCase.userJoinedGame()

}