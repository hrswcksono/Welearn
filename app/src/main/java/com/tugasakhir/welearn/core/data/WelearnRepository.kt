package com.tugasakhir.welearn.core.data

import com.tugasakhir.welearn.core.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.core.utils.DataMapper
import com.tugasakhir.welearn.domain.model.*
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WelearnRepository (private val remoteDataSource: RemoteDataSource): IWelearnRepository{
    override fun loginUser(username: String, password: String): Flow<Login>  =
        remoteDataSource.loginUser(username, password).map { DataMapper.mapperLoginToken(it) }

    override fun detailUser(token: String): Flow<User> =
        remoteDataSource.detailUser(token).map { DataMapper.mapperDetailUser(it) }

    override fun registerUser(
        username: String,
        password: String,
        email: String,
        name: String,
        jenis_kelamin: String
    ) : String = TODO("Not yet implemented")


    override fun logoutUser(token: String): String = TODO("Not yet implemented")
//        remoteDataSource.logoutUser(token)

    override fun randAngka(level: String, token: String): Flow<List<Soal>> =
        remoteDataSource.randAngka(level,token).map { DataMapper.mapperRandomSoal(it) }

    override fun randHuruf(level: String, token: String): Flow<List<Soal>> =
        remoteDataSource.randHuruf(level, token).map { DataMapper.mapperRandomSoal(it) }

    override fun scoreAngka(token: String): Flow<Score> {
        TODO("Not yet implemented")
    }

    override fun scoreHuruf(token: String): Flow<Score> {
        TODO("Not yet implemented")
    }

    override fun highScoreAngka(token: String): Flow<List<UserScore>> =
        remoteDataSource.highScoreAngka(token).map { DataMapper.mapperHighScore(it) }

    override fun highScoreHuruf(token: String): Flow<List<UserScore>> =
        remoteDataSource.highScoreHuruf(token).map { DataMapper.mapperHighScore(it) }


}