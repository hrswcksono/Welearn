package com.tugasakhir.welearn.data

import com.tugasakhir.welearn.utils.DataMapper
import com.tugasakhir.welearn.data.source.remote.IMultiplayerDataSource
import com.tugasakhir.welearn.domain.entity.*
import com.tugasakhir.welearn.domain.repository.IMultiPlayerRepository
import kotlinx.coroutines.flow.map

class MultiPlayerRepository constructor(
    private val remoteDataSource: IMultiplayerDataSource
): IMultiPlayerRepository {

    override fun getIDSoalMulti(jenis: Int, level: Int, authToken: String) =
        remoteDataSource.getIDSoalMulti(jenis, level, authToken).map { DataMapper.mapIDSoalMulti(it) }

    override fun getSoalByID(id: Int, authToken: String) =
        remoteDataSource.getSoalByID(id, authToken).map { DataMapper.mapperSoal(it) }

    override fun pushNotification(body: PushNotification) =
        remoteDataSource.pushNotification(body)

    override fun makeRoom(id_jenis: Int, id_level: Int, authToken: String) =
        remoteDataSource.makeRoom(id_jenis, id_level, authToken).map { DataMapper.mapperRoom(it.data!!) }

    override fun joinGame(idRoom: Int, authToken: String) =
        remoteDataSource.joinGame(idRoom, authToken).map { DataMapper.mapperJoinGame(it) }

    override fun gameAlreadyEnd(idGame: String, authToken: String) =
        remoteDataSource.gameAlreadyEnd(idGame, authToken).map { DataMapper.mapperGameAlreadyEnd(it) }

    override fun forceEndGame(idGame: String, authToken: String) =
        remoteDataSource.forceEndGame(idGame, authToken).map { DataMapper.mapperForcedEndGame(it) }

    override fun getListScoreMulti(idGame: Int, authToken: String) =
        remoteDataSource.getListScoreMulti(idGame, authToken).map { DataMapper.mapperScoreMulti(it) }

    override fun savePredictHurufMulti(
        idGame: Int,
        idSoal: Int,
        score: Int,
        duration: Int,
        authToken: String
    ) = remoteDataSource.savePredictHurufMulti(idGame, idSoal,score, duration, authToken).map {
        DataMapper.mapperPredictMulti(it)
    }

    override fun savePredictAngkaMulti(
        idGame: Int,
        idSoal: Int,
        score: Int,
        duration: Int,
        authToken: String
    ) = remoteDataSource.savePredictAngkaMulti(idGame, idSoal, score, duration, authToken).map {
        DataMapper.mapperPredictMulti(it)
    }

    override fun getListUserJoin(authToken: String) =
        remoteDataSource.getListUserJoin(authToken).map { DataMapper.mapperJoinedGame(it) }

    override fun getListUserParticipant(idGame: Int, authToken: String) =
        remoteDataSource.getListUserParticipant(idGame, authToken).map { DataMapper.mapperUserParticipant(it) }
}