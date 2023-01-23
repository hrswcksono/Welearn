package com.tugasakhir.welearn.domain.usecase.soal

import com.tugasakhir.welearn.domain.entity.IDSoalMultiEntity
import com.tugasakhir.welearn.domain.entity.SoalEntity
import com.tugasakhir.welearn.domain.repository.ISoalRepsoitory
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import kotlinx.coroutines.flow.Flow

class SoalInteractor (private val welearnRepository: ISoalRepsoitory): SoalUseCase {
    override fun getIDSoalMultiplayer(jenis: Int, level: Int) = welearnRepository.getIDSoalMultiplayer(jenis, level)
    override fun getSoalRandomSinglePlayer(jenis: Int, level: Int) = welearnRepository.getRandSoalSingle(jenis, level)
    override fun getSoalByID(id: Int) = welearnRepository.soalByID(id)
}