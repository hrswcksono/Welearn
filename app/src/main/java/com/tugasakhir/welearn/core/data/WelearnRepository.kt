package com.tugasakhir.welearn.core.data

import com.tugasakhir.welearn.core.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.core.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.core.utils.DataMapper
import com.tugasakhir.welearn.domain.model.*
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WelearnRepository (private val remoteDataSource: RemoteDataSource): IWelearnRepository{
    override fun loginUser(username: String, password: String) =
        remoteDataSource.loginUser(username, password).map { DataMapper.mapperLoginToken(it) }

    override fun detailUser(token: String) =
        remoteDataSource.detailUser(token).map { DataMapper.mapperDetailUser(it) }

    override fun registerUser(
        username: String,
        password: String,
        email: String,
        name: String,
        jenis_kelamin: String
    ) = remoteDataSource.registerUser(username, password, email, name, jenis_kelamin).map {
        DataMapper.mapperRegister(it)
    }

    override fun logoutUser(token: String) =
        remoteDataSource.logoutUser(token).map { DataMapper.mapperLogout(it) }

    override fun randAngka(level: Int, token: String) =
        remoteDataSource.randAngka(level,token).map { DataMapper.mapperRandomSoal(it) }

    override fun randHuruf(level: Int, token: String) =
        remoteDataSource.randHuruf(level, token).map { DataMapper.mapperRandomSoal(it) }

    override fun soalAngkaMultiplayer(level: Int, token: String) =
        remoteDataSource.soalAngkaMultiplayer(level, token).map { DataMapper.mapperSoal(it) }

    override fun soalHurufMultiplayer(level: Int, token: String) =
        remoteDataSource.soalHurufMultiplayer(level, token).map { DataMapper.mapperSoal(it) }

    override fun soalAngkaByID(id: Int, token: String) =
        remoteDataSource.soalAngkaByID(id, token).map { DataMapper.mapperSoal(it) }

    override fun soalHurufByID(id: Int, token: String) =
        remoteDataSource.soalHurufByID(id, token).map { DataMapper.mapperSoal(it) }

    override fun scoreAngka(token: String): Flow<Score> {
        TODO("Not yet implemented")
    }

    override fun scoreHuruf(token: String): Flow<Score> {
        TODO("Not yet implemented")
    }

    override fun highScoreAngka(token: String) =
        remoteDataSource.highScoreAngka(token).map { DataMapper.mapperHighScore(it) }

    override fun highScoreHuruf(token: String) =
        remoteDataSource.highScoreHuruf(token).map { DataMapper.mapperHighScore(it) }

    override fun pushNotification(body: PushNotification) =
        remoteDataSource.pushNotification(body)

    override fun predictAngka(
        id_soal: String,
        image: ArrayList<String>,
        token: String
    ) = remoteDataSource.predictAngka(id_soal, image, token).map { DataMapper.mapperPredict(it) }

    override fun predictHuruf(
        id_soal: String,
        image: ArrayList<String>,
        token: String
    ) = remoteDataSource.predictHuruf(id_soal, image, token).map { DataMapper.mapperPredict(it) }

}