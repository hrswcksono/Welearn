package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class UserParticipantPresenter(private val useCase: WelearnUseCase): ViewModel() {

    fun getListUserParticipant(idGame: Int) = useCase.getUserParticipant(idGame)

}