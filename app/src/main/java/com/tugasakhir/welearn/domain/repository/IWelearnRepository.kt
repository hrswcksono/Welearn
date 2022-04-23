package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.core.data.source.remote.response.LoginResponse
import com.tugasakhir.welearn.domain.model.Login
import com.tugasakhir.welearn.domain.model.User
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface IWelearnRepository {
    fun loginUser(username: String, password: String): Flow<Login>
    fun detailUser(token: String): Flow<User>
}