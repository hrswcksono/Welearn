package com.tugasakhir.welearn.data

import com.tugasakhir.welearn.utils.DataMapper
import com.tugasakhir.welearn.data.source.remote.ISinglePlayerDataSource
import com.tugasakhir.welearn.domain.repository.ISinglePlayerRepository
import kotlinx.coroutines.flow.map

class SinglePlayerRepository constructor(
    private val remoteDataSource: ISinglePlayerDataSource
): ISinglePlayerRepository {

    override fun scoreUser(id: Int, authToken: String) =
        remoteDataSource.scoreUser(id, authToken).map { DataMapper.mapScoreUser(it) }

    override fun highScore(id: Int, authToken: String) =
        remoteDataSource.highScore(id, authToken).map { DataMapper.mapperHighScore(it) }

    override fun predictAngka(
        idSoal: Int,
        score: Int,
        authToken: String
    ) = remoteDataSource.predictAngka(idSoal, score, authToken).map { DataMapper.mapperPredict(it) }

    override fun predictHuruf(
        idSoal: Int,
        score: Int,
        authToken: String
    ) = remoteDataSource.predictHuruf(idSoal, score, authToken).map { DataMapper.mapperPredict(it) }

    override fun getLevel(idLevel: Int, authToken: String) =
        remoteDataSource.levelSoal(idLevel, authToken).map { DataMapper.mapperLevel(it) }

    override fun getRandSoalSingle(jenis: Int, level: Int, authToken: String) =
        remoteDataSource.randSoalSingle(jenis, level, authToken).map { DataMapper.mapperRandomSoal(it) }
}