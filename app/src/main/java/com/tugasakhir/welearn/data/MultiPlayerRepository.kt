package com.tugasakhir.welearn.data

import com.tugasakhir.welearn.core.utils.DataMapper
import com.tugasakhir.welearn.core.utils.SessionManager
import com.tugasakhir.welearn.data.source.remote.MultiPlayerDataSource
import com.tugasakhir.welearn.domain.entity.*
import com.tugasakhir.welearn.domain.repository.IMultiPlayerRepository
import kotlinx.coroutines.flow.map

class MultiPlayerRepository constructor(
    private val remoteDataSource: MultiPlayerDataSource,
    private val sesi: SessionManager
): IMultiPlayerRepository {

    override fun getIDSoalMultiplayer(jenis: Int, level: Int) =
        remoteDataSource.soalMultiplayer(jenis, level, sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map { DataMapper.mapIDSoalMulti(it) }

    override fun soalByID(id: Int) =
        remoteDataSource.soalByID(id, sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map { DataMapper.mapperSoal(it) }

    override fun pushNotification(body: PushNotification) =
        remoteDataSource.pushNotification(body)

    override fun makeRoomGame(id_jenis: Int, id_level: Int) =
        remoteDataSource.makeRoomGame(id_jenis, id_level, sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map { DataMapper.mapperString(it) }

    override fun joinGame(idGame: String) =
        remoteDataSource.joinGame(idGame, sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map { DataMapper.mapperString(it) }

    override fun endGame(idGame: String) =
        remoteDataSource.endGame(idGame, sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map { DataMapper.mapperString(it) }

    override fun scoreMulti(idGame: Int) =
        remoteDataSource.scoreMulti(idGame, sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map { DataMapper.mapperScoreMulti(it) }

    override fun predictHurufMulti(
        idGame: Int,
        idSoal: Int,
        score: Int,
        duration: Int
    ) = remoteDataSource.predictHurufMulti(idGame, idSoal,score, duration, sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map {
        DataMapper.mapperString(it)
    }

    override fun predictAngkaMulti(
        idGame: Int,
        idSoal: Int,
        score: Int,
        duration: Int
    ) = remoteDataSource.predictAngkaMulti(idGame, idSoal, score, duration, sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map {
        DataMapper.mapperString(it)
    }

    override fun getJoinedGame() =
        remoteDataSource.getJoinedGame(sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map { DataMapper.mapperJoinedGame(it) }

    override fun getUserParticipant(idGame: Int) =
        remoteDataSource.userParticipant(idGame, sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map { DataMapper.mapperUserParticipant(it) }
}