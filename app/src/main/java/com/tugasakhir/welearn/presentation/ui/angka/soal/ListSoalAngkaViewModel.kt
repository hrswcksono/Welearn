package com.tugasakhir.welearn.presentation.ui.angka.soal

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class ListSoalAngkaViewModel(private val useCase: WelearnUseCase): ViewModel() {

    fun randomAngka(level:Int, token:String) : Flow<List<Soal>> = useCase.angkaRandom(level, token)
}