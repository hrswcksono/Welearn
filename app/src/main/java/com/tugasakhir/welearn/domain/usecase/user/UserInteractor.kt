package com.tugasakhir.welearn.domain.usecase.user

import com.tugasakhir.welearn.domain.entity.LoginEntity
import com.tugasakhir.welearn.domain.entity.ProfileEntity
import com.tugasakhir.welearn.domain.repository.IUserRepository
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import com.tugasakhir.welearn.domain.usecase.WelearnUseCase
import kotlinx.coroutines.flow.Flow

class UserInteractor (private val welearnRepository: IUserRepository): UserUseCase {
    override fun userLogin(username: String, password: String) = welearnRepository.loginUser(username, password)

    override fun userDetail() = welearnRepository.detailUser()

    override fun userRegister(
        username: String,
        password: String,
        email: String,
        name: String,
        jenisKelamin: String
    ) = welearnRepository.registerUser(username, password, email, name, jenisKelamin)

    override fun userLogout() = welearnRepository.logoutUser()
}