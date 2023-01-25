package com.tugasakhir.welearn.data

import com.tugasakhir.welearn.core.utils.DataMapper
import com.tugasakhir.welearn.core.utils.SessionManager
import com.tugasakhir.welearn.data.source.remote.SinglePlayerDataSource
import com.tugasakhir.welearn.domain.entity.*
import com.tugasakhir.welearn.domain.repository.ISinglePlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SinglePlayerRepository constructor(
    private val remoteDataSource: SinglePlayerDataSource,
    private val sesi: SessionManager
): ISinglePlayerRepository {

    override fun scoreUser(id: Int) =
        remoteDataSource.scoreUser(id, sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map { DataMapper.mapScoreUser(it) }

    override fun highScore(id: Int) =
        remoteDataSource.highScore(id, sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map { DataMapper.mapperHighScore(it) }

    override fun predictAngka(
        idSoal: Int,
        score: Int
    ) = remoteDataSource.predictAngka(idSoal, score, sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map { DataMapper.mapperPredict(it) }

    override fun predictHuruf(
        idSoal: Int,
        score: Int
    ) = remoteDataSource.predictHuruf(idSoal, score, sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map { DataMapper.mapperPredict(it) }

    override fun getLevel(idLevel: Int) =
        remoteDataSource.levelSoal(idLevel, sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map { DataMapper.mapperLevel(it) }

    override fun getRandSoalSingle(jenis: Int, level: Int) =
        remoteDataSource.randSoalSingle(jenis, level, sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map { DataMapper.mapperRandomSoal(it) }
}