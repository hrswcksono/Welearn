package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.core.data.source.remote.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface IWelearnRepository {
    fun loginUser(username: String, password: String): Flow<LoginResponse>
}