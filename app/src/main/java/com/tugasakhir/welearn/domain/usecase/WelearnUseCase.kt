package com.tugasakhir.welearn.domain.usecase

import com.tugasakhir.welearn.domain.model.Login
import com.tugasakhir.welearn.domain.model.User
import kotlinx.coroutines.flow.Flow

interface WelearnUseCase {
    fun userLogin(username: String, password: String) : Flow<Login>
    fun userDetail(token: String) : Flow<User>
}