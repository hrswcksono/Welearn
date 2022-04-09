package com.tugasakhir.welearn.domain.repository

import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface IWelearnRepository {
    fun loginUser(body: RequestBody): Flow<LoginResponse>
}