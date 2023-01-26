package com.tugasakhir.welearn.data

import com.tugasakhir.welearn.core.utils.DataMapper
import com.tugasakhir.welearn.core.utils.SessionManager
import com.tugasakhir.welearn.data.source.remote.AuthDataSource
import com.tugasakhir.welearn.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.map

class AuthRepository constructor(
    private val remoteDataSource: AuthDataSource
): IAuthRepository {

    override fun loginUser(username: String, password: String) =
        remoteDataSource.loginUser(username, password).map {
            DataMapper.mapperLogin(it)
        }

    override fun detailUser(authToken: String) =
        remoteDataSource.detailUser(authToken).map { DataMapper.mapperDetailUser(it) }

    override fun registerUser(
        username: String,
        password: String,
        email: String,
        name: String,
        jenisKelamin: String
    ) = remoteDataSource.registerUser(username, password, email, name, jenisKelamin).map {
        DataMapper.mapperRegister(it)
    }

    override fun logoutUser(authToken: String) =
        remoteDataSource.logoutUser(authToken).map { DataMapper.mapperString(it) }
}