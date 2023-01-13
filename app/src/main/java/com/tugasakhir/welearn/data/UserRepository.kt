package com.tugasakhir.welearn.data

import com.tugasakhir.welearn.core.utils.DataMapper
import com.tugasakhir.welearn.core.utils.SessionManager
import com.tugasakhir.welearn.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.domain.entity.LoginEntity
import com.tugasakhir.welearn.domain.entity.ProfileEntity
import com.tugasakhir.welearn.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val sesi: SessionManager
): IUserRepository{
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