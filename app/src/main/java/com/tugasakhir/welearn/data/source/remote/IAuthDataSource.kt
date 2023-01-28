package com.tugasakhir.welearn.data.source.remote

import com.tugasakhir.welearn.data.source.remote.response.*
import kotlinx.coroutines.flow.Flow

interface IAuthDataSource {
    fun loginUser(username: String, password: String): Flow<LoginResponse>
    fun detailUser(tokenUser: String): Flow<DMessage>
    fun registerUser(
        username: String,
        password: String,
        email: String,
        name: String,
        jenisKelamin: String
    ): Flow<RegisterResponse>
    fun logoutUser(tokenUser: String): Flow<SimpleResponse>
}