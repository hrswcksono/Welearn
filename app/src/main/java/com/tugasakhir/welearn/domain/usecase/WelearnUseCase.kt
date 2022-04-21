package com.tugasakhir.welearn.domain.usecase

import com.tugasakhir.welearn.domain.model.Login
import kotlinx.coroutines.flow.Flow

interface WelearnUseCase {
    fun userLogin(username: String, password: String) : Flow<Login>
}