package com.tugasakhir.welearn.data

import com.tugasakhir.welearn.core.utils.DataMapper
import com.tugasakhir.welearn.core.utils.SessionManager
import com.tugasakhir.welearn.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.domain.entity.IDSoalMultiEntity
import com.tugasakhir.welearn.domain.entity.SoalEntity
import com.tugasakhir.welearn.domain.repository.ISoalRepsoitory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SoalRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val sesi: SessionManager
): ISoalRepsoitory {

    var token = sesi.getFromPreference(SessionManager.KEY_LOGIN)!!

    override fun getIDSoalMultiplayer(jenis: Int, level: Int) =
        remoteDataSource.soalMultiplayer(jenis, level, token).map { DataMapper.mapIDSoalMulti(it) }

    override fun soalByID(id: Int) =
        remoteDataSource.soalByID(id, token).map { DataMapper.mapperSoal(it) }

    override fun getRandSoalSingle(jenis: Int, level: Int) =
        remoteDataSource.randSoalSingle(jenis, level, token).map { DataMapper.mapperRandomSoal(it) }
}