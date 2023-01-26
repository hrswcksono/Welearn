package com.tugasakhir.welearn.domain.usecase.auth

import com.tugasakhir.welearn.domain.entity.LoginEntity
import com.tugasakhir.welearn.domain.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {
    fun userLogin(username: String, password: String) : Flow<LoginEntity>
    fun userDetail(authToken: String) : Flow<ProfileEntity>
    fun userRegister(username: String, password: String, email: String, name: String, jenisKelamin: String): Flow<String>
    fun userLogout(authToken: String): Flow<String>
}