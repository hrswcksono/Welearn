package com.tugasakhir.welearn.domain.usecase

import com.tugasakhir.welearn.core.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.model.*
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import kotlinx.coroutines.flow.Flow

class WelearnInteractor(private val welearnRepository: IWelearnRepository): WelearnUseCase {
    override fun userLogin(username: String, password: String): Flow<Login> =
        welearnRepository.loginUser(username, password)

    override fun userDetail(token: String): Flow<User> =
        welearnRepository.detailUser(token)

    override fun userRegister(
        username: String,
        password: String,
        email: String,
        name: String,
        jenis_kelamin: String
    ) : Flow<String> =
        welearnRepository.registerUser(username, password, email, name, jenis_kelamin)

    override fun userLogout(token: String): Flow<String> =
        welearnRepository.logoutUser(token)

    override fun angkaRandom(level: Int, token: String): Flow<List<Soal>> = welearnRepository.randAngka(level, token)
    override fun hurufRandom(level: Int, token: String): Flow<List<Soal>> = welearnRepository.randHuruf(level, token)

    override fun soalMultiplayerAngka(level: Int, token: String): Flow<Soal> =
        welearnRepository.soalAngkaMultiplayer(level, token)
    override fun soalMultiplayerHuruf(level: Int, token: String): Flow<Soal> =
        welearnRepository.soalHurufMultiplayer(level, token)

    override fun getSoalAngkaByID(id: Int, token: String): Flow<Soal> =
        welearnRepository.soalAngkaByID(id, token)

    override fun getSoalHurufByID(id: Int, token: String): Flow<Soal> =
        welearnRepository.soalHurufByID(id, token)

    override fun angkaHighScore(token: String): Flow<List<UserScore>> =
        welearnRepository.highScoreAngka(token)

    override fun hurufHighScore(token: String): Flow<List<UserScore>> =
        welearnRepository.highScoreHuruf(token)

    override fun pushNotification(body: PushNotification): Flow<PushNotificationResponse> =
        welearnRepository.pushNotification(body)

}