package com.tugasakhir.welearn.data

import com.tugasakhir.welearn.core.utils.DataMapper
import com.tugasakhir.welearn.core.utils.SessionManager
import com.tugasakhir.welearn.data.source.remote.AuthDataSource
import com.tugasakhir.welearn.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.map

class AuthRepository constructor(
    private val remoteDataSource: AuthDataSource,
    private val sesi: SessionManager
): IAuthRepository {
    override fun loginUser(username: String, password: String) =
        remoteDataSource.loginUser(username, password).map {
            sesi.saveToPreference(SessionManager.KEY_LOGIN, it.token!!)
            DataMapper.mapperLogin(it)
        }

    override fun detailUser() =
        remoteDataSource.detailUser(sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map { DataMapper.mapperDetailUser(it) }

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
        remoteDataSource.logoutUser(sesi.getFromPreference(SessionManager.KEY_LOGIN)!!).map { DataMapper.mapperString(it) }
}