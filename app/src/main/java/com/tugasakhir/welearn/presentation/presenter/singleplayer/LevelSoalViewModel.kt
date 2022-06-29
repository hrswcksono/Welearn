package com.tugasakhir.welearn.presentation.presenter.singleplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class LevelSoalViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun getLevelSoal(id_level: Int, token: String) = useCase.getLevel(id_level, token)
}