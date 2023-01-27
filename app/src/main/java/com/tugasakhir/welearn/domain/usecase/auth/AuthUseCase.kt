package com.tugasakhir.welearn.domain.usecase.auth

import com.tugasakhir.welearn.domain.entity.Login
import com.tugasakhir.welearn.domain.entity.Profile
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {
    fun userLogin(username: String, password: String) : Flow<Login>
    fun userDetail(authToken: String) : Flow<Profile>
    fun userRegister(username: String, password: String, email: String, name: String, jenisKelamin: String): Flow<String>
    fun userLogout(authToken: String): Flow<String>
}