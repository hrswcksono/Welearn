package com.tugasakhir.welearn.domain.usecase

import com.tugasakhir.welearn.domain.model.Login
import com.tugasakhir.welearn.domain.model.User
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import kotlinx.coroutines.flow.Flow

class WelearnInteractor(private val welearnRepository: IWelearnRepository): WelearnUseCase {
    override fun userLogin(username: String, password: String): Flow<Login> {
        return welearnRepository.loginUser(username, password)
    }

    override fun userDetail(token: String): Flow<User> {
        return welearnRepository.detailUser(token)
    }

}