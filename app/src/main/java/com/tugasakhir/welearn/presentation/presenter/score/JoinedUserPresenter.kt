package com.tugasakhir.welearn.presentation.presenter.score

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class JoinedUserPresenter(private val useCase: WelearnUseCase): ViewModel() {

    fun getJoinedGame() = useCase.userJoinedGame()

}