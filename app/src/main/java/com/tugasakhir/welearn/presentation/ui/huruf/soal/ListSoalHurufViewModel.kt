package com.tugasakhir.welearn.presentation.ui.huruf.soal

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class ListSoalHurufViewModel(private val useCase: WelearnUseCase): ViewModel() {
    fun randomHuruf(level: Int, token: String) : Flow<List<Soal>> = useCase.hurufRandom(level, token)
}