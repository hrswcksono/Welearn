package com.tugasakhir.welearn.domain.usecase.user

import com.tugasakhir.welearn.domain.entity.LoginEntity
import com.tugasakhir.welearn.domain.entity.ProfileEntity
import com.tugasakhir.welearn.domain.entity.ScoreEntity
import com.tugasakhir.welearn.domain.entity.SoalEntity
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun userLogin(username: String, password: String) : Flow<LoginEntity>
    fun userDetail() : Flow<ProfileEntity>
    fun userRegister(username: String, password: String, email: String, name: String, jenisKelamin: String): Flow<String>
    fun userLogout(): Flow<String>
}