package com.tugasakhir.welearn.data

import com.tugasakhir.welearn.data.source.local.LocalDataSource
import com.tugasakhir.welearn.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.data.source.remote.network.ApiResponse
import com.tugasakhir.welearn.data.source.remote.response.LevelResponse
import com.tugasakhir.welearn.data.source.remote.response.RandomSoalResponse
import com.tugasakhir.welearn.data.source.remote.response.UserParticipatedResponse
import com.tugasakhir.welearn.core.utils.DataMapper
import com.tugasakhir.welearn.domain.entity.*
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WelearnRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
    ): IWelearnRepository{

    var token = ""

    override fun loginUser(username: String, password: String) =
        remoteDataSource.loginUser(username, password).map {
            token = it.token.toString()
            DataMapper.mapperLogin(it)
        }

    override fun detailUser() =
        remoteDataSource.detailUser(token).map { DataMapper.mapperDetailUser(it) }

    override fun registerUser(
        username: String,
        password: String,
        email: String,
        name: String,
        jenisKelamin: String
    ) = remoteDataSource.registerUser(username, password, email, name, jenisKelamin).map {
        DataMapper.mapperRegister(it)
    }

    override fun logoutUser() =
        remoteDataSource.logoutUser(token).map { DataMapper.mapperString(it) }

    override fun getIDSoalMultiplayer(jenis: Int, level: Int) =
        remoteDataSource.soalMultiplayer(jenis, level, token).map { DataMapper.mapIDSoalMulti(it) }

    override fun soalByID(id: Int) =
        remoteDataSource.soalByID(id, token).map { DataMapper.mapperSoal(it) }

    override fun scoreUser(id: Int) =
        remoteDataSource.scoreUser(id, token).map { DataMapper.mapScoreUser(it) }

    override fun highScore(id: Int) =
        remoteDataSource.highScore(id, token).map { DataMapper.mapperHighScore(it) }

    override fun pushNotification(body: PushNotification) =
        remoteDataSource.pushNotification(body)

    override fun predictAngka(
        idSoal: Int,
        score: Int
    ) = remoteDataSource.predictAngka(idSoal, score, token).map { DataMapper.mapperPredict(it) }

    override fun predictHuruf(
        idSoal: Int,
        score: Int
    ) = remoteDataSource.predictHuruf(idSoal, score, token).map { DataMapper.mapperPredict(it) }

    override fun makeRoomGame(id_jenis: Int, id_level: Int) =
        remoteDataSource.makeRoomGame(id_jenis, id_level, token).map { DataMapper.mapperString(it) }

    override fun joinGame(idGame: String) =
        remoteDataSource.joinGame(idGame, token).map { DataMapper.mapperString(it) }

    override fun endGame(idGame: String) =
        remoteDataSource.endGame(idGame, token).map { DataMapper.mapperString(it) }

    override fun scoreMulti(idGame: Int) =
        remoteDataSource.scoreMulti(idGame, token).map { DataMapper.mapperScoreMulti(it) }

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

    override fun getLevel(idLevel: Int) =
        remoteDataSource.levelSoal(idLevel, token).map { DataMapper.mapperLevel(it) }

    override fun getRandSoalSingle(jenis: Int, level: Int) =
        remoteDataSource.randSoalSingle(jenis, level, token).map { DataMapper.mapperRandomSoal(it) }

    override fun getUserParticipant(idGame: Int): Flow<Resource<List<UserPaticipantEntity>>> =
        object : NetworkBoundResource<List<UserPaticipantEntity>, List<UserParticipatedResponse>>() {
            override fun loadFromDB(): Flow<List<UserPaticipantEntity>> =
                localDataSource.getUserParticipant(idGame).map { DataMapper.mapEntitiesToDomainUserParticipant(it) }
            override fun shouldFetch(data: List<UserPaticipantEntity>?): Boolean =
                data == null || data.isEmpty()
            override suspend fun createCall(): Flow<ApiResponse<List<UserParticipatedResponse>>> =
                remoteDataSource.userParticipant(idGame, token)
            override suspend fun saveCallResult(data: List<UserParticipatedResponse>) {
                val userList = DataMapper.mapResponseToEntitiesUserParticipant(data)
                localDataSource.insertUserParticipant(userList)
            }
        }.asFlow()
}