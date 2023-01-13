package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.domain.entity.IDSoalMultiEntity
import com.tugasakhir.welearn.domain.entity.SoalEntity
import kotlinx.coroutines.flow.Flow

interface ISoalRepsoitory {
    fun getIDSoalMultiplayer(jenis: Int, level: Int): Flow<IDSoalMultiEntity>
    fun soalByID(id: Int): Flow<SoalEntity>
    fun getRandSoalSingle(jenis: Int ,level: Int): Flow<List<SoalEntity>>
}