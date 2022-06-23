package com.tugasakhir.welearn.presentation.viewmodel.score

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase

class SoalByIDViewModel (private val useCase: WelearnUseCase): ViewModel() {
    fun getSoalByID(id: Int,token: String) = useCase.getSoalByID(id, token)
}