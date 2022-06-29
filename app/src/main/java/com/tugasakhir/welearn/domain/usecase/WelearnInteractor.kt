package com.tugasakhir.welearn.domain.usecase

import com.tugasakhir.welearn.core.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.model.*
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import kotlinx.coroutines.flow.Flow

class WelearnInteractor(private val welearnRepository: IWelearnRepository): WelearnUseCase {
    override fun userLogin(username: String, password: String) =
        welearnRepository.loginUser(username, password)

    override fun userDetail(token: String) =
        welearnRepository.detailUser(token)

    override fun userRegister(
        username: String,
        password: String,
        email: String,
        name: String,
        jenis_kelamin: String
    ) = welearnRepository.registerUser(username, password, email, name, jenis_kelamin)

    override fun userLogout(token: String) =
        welearnRepository.logoutUser(token)

    override fun angkaRandom(level: Int, token: String) =
        welearnRepository.randAngka(level, token)

    override fun hurufRandom(level: Int, token: String) =
        welearnRepository.randHuruf(level, token)

    override fun soalMultiplayerAngka(level: Int, token: String) =
        welearnRepository.soalAngkaMultiplayer(level, token)

    override fun soalMultiplayerHuruf(level: Int, token: String) =
        welearnRepository.soalHurufMultiplayer(level, token)

    override fun getSoalByID(id: Int, token: String) =
        welearnRepository.soalByID(id, token)

//    override fun getSoalAngkaByID(id: Int, token: String) =
//        welearnRepository.soalAngkaByID(id, token)
//
//    override fun getSoalHurufByID(id: Int, token: String) =
//        welearnRepository.soalHurufByID(id, token)



    override fun userAngkaScore(token: String) =
        welearnRepository.scoreAngkaUser(token)

    override fun userHurufScore(token: String) =
        welearnRepository.scoreHurufUser(token)

    override fun angkaHighScore(token: String) =
        welearnRepository.highScoreAngka(token)

    override fun hurufHighScore(token: String) =
        welearnRepository.highScoreHuruf(token)

    override fun pushNotification(body: PushNotification) =
        welearnRepository.pushNotification(body)

    override fun pushNotificationStart(body: PushNotificationStart) =
        welearnRepository.pushStartNotification(body)

    override fun angkaPredict(
        id_soal: String,
        image: ArrayList<String>,
        token: String
    ) = welearnRepository.predictAngka(id_soal, image, token)

    override fun hurufPredict(
        id_soal: String,
        image: ArrayList<String>,
        token: String
    ) = welearnRepository.predictHuruf(id_soal, image, token)

    override fun testPredict(input: String, token: String) =
        welearnRepository.predictTest(input, token)

    override fun makeRoomGame(token: String) =
        welearnRepository.makeRoomGame(token)

    override fun joinGame(id_game: String, token: String) =
        welearnRepository.joinGame(id_game, token)

    override fun endGame(id_game: String, token: String) =
        welearnRepository.endGame(id_game, token)

    override fun scoreMulti(id_game: Int, token: String) =
        welearnRepository.scoreMulti(id_game, token)

    override fun predictHurufMulti(
        id_game: Int,
        id_soal: Int,
        duration: Int,
        token: String
    ) = welearnRepository.predictHurufMulti(id_game, id_soal, duration, token)

    override fun predictAngkaMulti(
        id_game: Int,
        id_jenis: Int,
        image: ArrayList<String>,
        token: String
    ) = welearnRepository.predictAngkaMulti(id_game, id_jenis, image, token)

    override fun getJoinedGame(token: String) =
        welearnRepository.getJoinedGame(token)

    override fun getLevel(id_level: Int, token: String) =
        welearnRepository.getLevel(id_level, token)

}