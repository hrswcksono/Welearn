package com.tugasakhir.welearn.data

import com.tugasakhir.welearn.core.utils.DataMapper
import com.tugasakhir.welearn.core.utils.SessionManager
import com.tugasakhir.welearn.data.source.remote.IMultiplayerDataSource
import com.tugasakhir.welearn.data.source.remote.MultiPlayerDataSource
import com.tugasakhir.welearn.domain.entity.*
import com.tugasakhir.welearn.domain.repository.IMultiPlayerRepository
import kotlinx.coroutines.flow.map

class MultiPlayerRepository constructor(
    private val remoteDataSource: IMultiplayerDataSource
): IMultiPlayerRepository {

    override fun getIDSoalMultiplayer(jenis: Int, level: Int, authToken: String) =
        remoteDataSource.soalMultiplayer(jenis, level, authToken).map { DataMapper.mapIDSoalMulti(it) }

    override fun soalByID(id: Int, authToken: String) =
        remoteDataSource.soalByID(id, authToken).map { DataMapper.mapperSoal(it) }

    override fun pushNotification(body: PushNotification) =
        remoteDataSource.pushNotification(body)

    override fun makeRoomGame(id_jenis: Int, id_level: Int, authToken: String) =
        remoteDataSource.makeRoomGame(id_jenis, id_level, authToken).map { DataMapper.mapperString(it) }

    override fun joinGame(idGame: String, authToken: String) =
        remoteDataSource.joinGame(idGame, authToken).map { DataMapper.mapperString(it) }

    override fun endGame(idGame: String, authToken: String) =
        remoteDataSource.endGame(idGame, authToken).map { DataMapper.mapperString(it) }

    override fun scoreMulti(idGame: Int, authToken: String) =
        remoteDataSource.scoreMulti(idGame, authToken).map { DataMapper.mapperScoreMulti(it) }

    override fun predictHurufMulti(
        idGame: Int,
        idSoal: Int,
        score: Int,
        duration: Int,
        authToken: String
    ) = remoteDataSource.predictHurufMulti(idGame, idSoal,score, duration, authToken).map {
        DataMapper.mapperString(it)
    }

    override fun predictAngkaMulti(
        idGame: Int,
        idSoal: Int,
        score: Int,
        duration: Int,
        authToken: String
    ) = remoteDataSource.predictAngkaMulti(idGame, idSoal, score, duration, authToken).map {
        DataMapper.mapperString(it)
    }

    override fun getJoinedGame(authToken: String) =
        remoteDataSource.getJoinedGame(authToken).map { DataMapper.mapperJoinedGame(it) }

    override fun getUserParticipant(idGame: Int, authToken: String) =
        remoteDataSource.userParticipant(idGame, authToken).map { DataMapper.mapperUserParticipant(it) }
}