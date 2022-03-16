package com.tugasakhir.welearn.core.data

import com.tugasakhir.welearn.core.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.core.data.source.remote.response.LoginResponse
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

class WelearnRepository (private val remoteDataSource: RemoteDataSource): IWelearnRepository{
    override fun loginUser(body: RequestBody): Flow<LoginResponse> = remoteDataSource.loginUser(body)
}