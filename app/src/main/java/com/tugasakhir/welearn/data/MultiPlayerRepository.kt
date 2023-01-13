package com.tugasakhir.welearn.data

import com.tugasakhir.welearn.core.utils.DataMapper
import com.tugasakhir.welearn.core.utils.SessionManager
import com.tugasakhir.welearn.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.entity.ScoreMultiEntity
import com.tugasakhir.welearn.domain.entity.UserJoinEntity
import com.tugasakhir.welearn.domain.entity.UserPaticipantEntity
import com.tugasakhir.welearn.domain.repository.IMultiPlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MultiPlayerRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val sesi: SessionManager
): IMultiPlayerRepository {

    var token = sesi.getFromPreference(SessionManager.KEY_LOGIN)!!

    override fun makeRoomGame(id_jenis: Int, id_level: Int) =
        remoteDataSource.makeRoomGame(id_jenis, id_level, token).map { DataMapper.mapperString(it) }

    override fun joinGame(idGame: String) =
        remoteDataSource.joinGame(idGame, token).map { DataMapper.mapperString(it) }

    override fun endGame(idGame: String) =
        remoteDataSource.endGame(idGame, token).map { DataMapper.mapperString(it) }

    override fun predictHurufMulti(
        idGame: Int,
        idSoal: Int,
        score: Int,
        duration: Int
    ) = remoteDataSource.predictHurufMulti(idGame, idSoal,score, duration, token).map {
        DataMapper.mapperString(it)
    }

    override fun predictAngkaMulti(
        idGame: Int,
        idSoal: Int,
        score: Int,
        duration: Int
    ) = remoteDataSource.predictAngkaMulti(idGame, idSoal, score, duration, token).map {
        DataMapper.mapperString(it)
    }

    override fun getJoinedGame() =
        remoteDataSource.getJoinedGame(token).map { DataMapper.mapperJoinedGame(it) }

    override fun pushNotification(body: PushNotification) =
        remoteDataSource.pushNotification(body)

    override fun getUserParticipant(idGame: Int) =
        remoteDataSource.userParticipant(idGame, token).map { DataMapper.mapperUserParticipant(it) }
}