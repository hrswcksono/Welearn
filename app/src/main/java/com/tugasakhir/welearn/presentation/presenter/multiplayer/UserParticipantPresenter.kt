package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase

class UserParticipantPresenter(private val useCase: MultiPlayerUseCase): ViewModel() {
    fun getListUserParticipant(idGame: Int) = useCase.getUserParticipant(idGame)
}