package com.tugasakhir.welearn.domain.usecase

import com.tugasakhir.welearn.domain.model.Login
import com.tugasakhir.welearn.domain.model.Soal
import com.tugasakhir.welearn.domain.model.User
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import kotlinx.coroutines.flow.Flow

class WelearnInteractor(private val welearnRepository: IWelearnRepository): WelearnUseCase {
    override fun userLogin(username: String, password: String): Flow<Login> = welearnRepository.loginUser(username, password)
    override fun userDetail(token: String): Flow<User> = welearnRepository.detailUser(token)
    override fun userRegister(
        username: String,
        password: String,
        email: String,
        name: String,
        jenis_kelamin: String
    ) : String = welearnRepository.registerUser(username, password, email, name, jenis_kelamin)

    override fun angkaRandom(level: Int, token: String): Flow<List<Soal>> = welearnRepository.randAngka(level, token)
    override fun hurufRandom(level: Int, token: String): Flow<List<Soal>> = welearnRepository.randHuruf(level, token)

}