package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase

class SoalByIDPresenter (private val useCase: MultiPlayerUseCase): ViewModel() {
    fun getSoalByID(id: Int) = useCase.getSoalByID(id)
}