package com.tugasakhir.welearn.presentation.presenter.score

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class SoalByIDViewModel (private val useCase: WelearnUseCase): ViewModel() {
    fun getSoalByID(id: Int) = useCase.getSoalByID(id)
}