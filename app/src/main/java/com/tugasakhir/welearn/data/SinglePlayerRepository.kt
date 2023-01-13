package com.tugasakhir.welearn.data

import com.tugasakhir.welearn.core.utils.DataMapper
import com.tugasakhir.welearn.core.utils.SessionManager
import com.tugasakhir.welearn.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.domain.entity.LevelEntity
import com.tugasakhir.welearn.domain.entity.ResultPredictEntity
import com.tugasakhir.welearn.domain.repository.ISinglePlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SinglePlayerRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val sesi: SessionManager
): ISinglePlayerRepository {

    var token = sesi.getFromPreference(SessionManager.KEY_LOGIN)!!

    override fun predictAngka(
        idSoal: Int,
        score: Int
    ) = remoteDataSource.predictAngka(idSoal, score, token).map { DataMapper.mapperPredict(it) }

    override fun predictHuruf(
        idSoal: Int,
        score: Int
    ) = remoteDataSource.predictHuruf(idSoal, score, token).map { DataMapper.mapperPredict(it) }

    override fun getLevel(idLevel: Int) =
        remoteDataSource.levelSoal(idLevel, token).map { DataMapper.mapperLevel(it) }
}