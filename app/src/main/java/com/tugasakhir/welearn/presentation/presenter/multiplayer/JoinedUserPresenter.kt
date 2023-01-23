package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase

class JoinedUserPresenter(private val useCase: MultiPlayerUseCase): ViewModel() {

    fun getJoinedGame() = useCase.userJoinedGame()

}