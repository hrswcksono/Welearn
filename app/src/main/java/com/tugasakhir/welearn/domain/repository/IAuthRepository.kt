package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.domain.entity.Login
import com.tugasakhir.welearn.domain.entity.Profile
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    fun loginUser(username: String, password: String): Flow<Login>
    fun detailUser(authToken: String): Flow<Profile>
    fun registerUser(username: String, password: String, email: String, name: String, jenisKelamin: String): Flow<String>
    fun logoutUser(authToken: String) : Flow<String>
}