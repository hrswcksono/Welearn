package com.tugasakhir.welearn.core.data

import com.tugasakhir.welearn.core.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.core.utils.DataMapper
import com.tugasakhir.welearn.domain.model.Login
import com.tugasakhir.welearn.domain.model.User
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WelearnRepository (private val remoteDataSource: RemoteDataSource): IWelearnRepository{
    override fun loginUser(username: String, password: String): Flow<Login>  =
        remoteDataSource.loginUser(username, password).map { DataMapper.mapperLoginToken(it) }

    override fun detailUser(token: String): Flow<User> =
        remoteDataSource.detailUser(token).map { DataMapper.mapperDetailUser(it) }

}