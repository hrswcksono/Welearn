package com.tugasakhir.welearn.domain.usecase.auth

import com.tugasakhir.welearn.domain.repository.IAuthRepository

class AuthInteractor (private val welearnRepository: IAuthRepository): AuthUseCase {
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