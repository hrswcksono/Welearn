package com.tugasakhir.welearn.domain.usecase.soal

import com.tugasakhir.welearn.domain.entity.IDSoalMultiEntity
import com.tugasakhir.welearn.domain.entity.SoalEntity
import kotlinx.coroutines.flow.Flow

interface SoalUseCase {
    fun getIDSoalMultiplayer(jenis: Int,level: Int): Flow<IDSoalMultiEntity>
    fun getSoalRandomSinglePlayer(jenis: Int, level: Int): Flow<List<SoalEntity>>
    fun getSoalByID(id: Int): Flow<SoalEntity>
}