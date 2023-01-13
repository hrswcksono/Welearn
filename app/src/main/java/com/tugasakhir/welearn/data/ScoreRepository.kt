package com.tugasakhir.welearn.data.source

import com.tugasakhir.welearn.core.utils.DataMapper
import com.tugasakhir.welearn.core.utils.SessionManager
import com.tugasakhir.welearn.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.domain.entity.RankingScoreEntity
import com.tugasakhir.welearn.domain.entity.ScoreEntity
import com.tugasakhir.welearn.domain.entity.ScoreMultiEntity
import com.tugasakhir.welearn.domain.repository.IScoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ScoreRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val sesi: SessionManager
): IScoreRepository {

    var token = sesi.getFromPreference(SessionManager.KEY_LOGIN)!!

    override fun scoreMulti(idGame: Int) =
        remoteDataSource.scoreMulti(idGame, token).map { DataMapper.mapperScoreMulti(it) }

    override fun scoreUser(id: Int) =
        remoteDataSource.scoreUser(id, token).map { DataMapper.mapScoreUser(it) }

    override fun highScore(id: Int) =
        remoteDataSource.highScore(id, token).map { DataMapper.mapperHighScore(it) }
}