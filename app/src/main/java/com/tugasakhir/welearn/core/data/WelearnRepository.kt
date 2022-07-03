package com.tugasakhir.welearn.core.data

import com.tugasakhir.welearn.core.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.core.utils.DataMapper
import com.tugasakhir.welearn.domain.model.*
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import kotlinx.coroutines.flow.map

class WelearnRepository (private val remoteDataSource: RemoteDataSource): IWelearnRepository{
    override fun loginUser(username: String, password: String) =
        remoteDataSource.loginUser(username, password).map { DataMapper.mapperLogin(it) }

    override fun detailUser() =
        remoteDataSource.detailUser().map { DataMapper.mapperDetailUser(it) }

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
        remoteDataSource.logoutUser().map { DataMapper.mapperString(it) }

    override fun randAngka(level: Int) =
        remoteDataSource.randAngka(level).map { DataMapper.mapperRandomSoal(it) }

    override fun randHuruf(level: Int) =
        remoteDataSource.randHuruf(level).map { DataMapper.mapperRandomSoal(it) }

    override fun soalAngkaMultiplayer(level: Int) =
        remoteDataSource.soalAngkaMultiplayer(level).map { DataMapper.mapperString(it) }

    override fun soalHurufMultiplayer(level: Int) =
        remoteDataSource.soalHurufMultiplayer(level).map { DataMapper.mapperString(it) }

    override fun soalByID(id: Int) =
        remoteDataSource.soalByID(id).map { DataMapper.mapperSoal(it) }

    override fun scoreAngkaUser() =
        remoteDataSource.scoreAngkaUser().map { DataMapper.mapScoreUser(it) }

    override fun scoreHurufUser() =
        remoteDataSource.scoreHurufUser().map { DataMapper.mapScoreUser(it) }

    override fun highScoreAngka() =
        remoteDataSource.highScoreAngka().map { DataMapper.mapperHighScore(it) }

    override fun highScoreHuruf() =
        remoteDataSource.highScoreHuruf().map { DataMapper.mapperHighScore(it) }

    override fun pushNotification(body: PushNotification) =
        remoteDataSource.pushNotification(body)

//    override fun pushStartNotification(body: PushNotificationStart) =
//        remoteDataSource.pushNotificationStart(body)

    override fun predictAngka(
        idSoal: Int,
        image: ArrayList<String>
    ) = remoteDataSource.predictAngka(idSoal, image).map { DataMapper.mapperPredict(it) }

    override fun predictHuruf(
        idSoal: Int,
        image: ArrayList<String>
    ) = remoteDataSource.predictHuruf(idSoal, image).map { DataMapper.mapperPredict(it) }

    override fun predictTest(input: String) =
        remoteDataSource.testPredict(input).map { DataMapper.mapperPredict(it) }

    override fun makeRoomGame(id_jenis: Int) =
        remoteDataSource.makeRoomGame(id_jenis).map { DataMapper.mapperString(it) }

    override fun joinGame(idGame: String) =
        remoteDataSource.joinGame(idGame).map { DataMapper.mapperString(it) }

    override fun endGame(idGame: String) =
        remoteDataSource.endGame(idGame).map { DataMapper.mapperString(it) }

    override fun scoreMulti(idGame: Int) =
        remoteDataSource.scoreMulti(idGame).map { DataMapper.mapperScoreMulti(it) }

    override fun predictHurufMulti(
        idGame: Int,
        idSoal: Int,
        image: ArrayList<String>,
        duration: Int
    ) = remoteDataSource.predictHurufMulti(idGame, idSoal,image, duration).map {
        DataMapper.mapperString(it)
    }

    override fun predictAngkaMulti(
        idGame: Int,idSoal: Int,image: ArrayList<String> , duration: Int
    ) = remoteDataSource.predictAngkaMulti(idGame, idSoal, image, duration).map {
        DataMapper.mapperString(it)
    }

    override fun getJoinedGame() =
        remoteDataSource.getJoinedGame().map {
            DataMapper.mapperJoinedGame(it)
        }

    override fun getLevel(idLevel: Int) =
        remoteDataSource.levelSoal(idLevel).map {
            DataMapper.mapperLevel(it)
        }
}