package com.tugasakhir.welearn.data.source.remote

import com.tugasakhir.welearn.data.source.remote.response.DMessage
import com.tugasakhir.welearn.data.source.remote.response.Message
import com.tugasakhir.welearn.data.source.remote.response.RegisterResponse
import com.tugasakhir.welearn.data.source.remote.response.SimpleResponse
import kotlinx.coroutines.flow.Flow

interface IAuthDataSource {
    fun loginUser(username: String, password: String): Flow<Message>
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