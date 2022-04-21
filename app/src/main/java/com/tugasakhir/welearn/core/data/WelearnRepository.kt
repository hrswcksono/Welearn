package com.tugasakhir.welearn.core.data

import com.tugasakhir.welearn.core.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.core.utils.DataMapper
import com.tugasakhir.welearn.domain.model.Login
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WelearnRepository (private val remoteDataSource: RemoteDataSource): IWelearnRepository{
        override fun loginUser(username: String, password: String): Flow<Login> {
        return remoteDataSource.loginUser(username, password).map {
            DataMapper.mapperLoginToken(it)
        }
    }
//    override fun loginUser(username: String, password: String): Flow<Login> {
//        TODO("Not yet implemented")
//    }

}