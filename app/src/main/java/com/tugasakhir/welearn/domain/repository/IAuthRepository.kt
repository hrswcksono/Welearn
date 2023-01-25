package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.domain.entity.LoginEntity
import com.tugasakhir.welearn.domain.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    fun loginUser(username: String, password: String): Flow<LoginEntity>
    fun detailUser(): Flow<ProfileEntity>
    fun registerUser(username: String, password: String, email: String, name: String, jenisKelamin: String): Flow<String>
    fun logoutUser() : Flow<String>
}