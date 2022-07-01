package com.tugasakhir.welearn.domain.usecase

import com.tugasakhir.welearn.domain.model.*
import com.tugasakhir.welearn.domain.repository.IWelearnRepository

class WelearnInteractor(private val welearnRepository: IWelearnRepository): WelearnUseCase {
    override fun userLogin(username: String, password: String) = welearnRepository.loginUser(username, password)

    override fun userDetail() = welearnRepository.detailUser()

    override fun userRegister(
        username: String,
        password: String,
        email: String,
        name: String,
        jenis_kelamin: String
    ) = welearnRepository.registerUser(username, password, email, name, jenis_kelamin)

    override fun userLogout() = welearnRepository.logoutUser()

    override fun angkaRandom(level: Int) = welearnRepository.randAngka(level)

    override fun hurufRandom(level: Int) = welearnRepository.randHuruf(level)

    override fun soalMultiplayerAngka(level: Int) = welearnRepository.soalAngkaMultiplayer(level)

    override fun soalMultiplayerHuruf(level: Int) = welearnRepository.soalHurufMultiplayer(level)

    override fun getSoalByID(id: Int) = welearnRepository.soalByID(id)

    override fun userAngkaScore() = welearnRepository.scoreAngkaUser()

    override fun userHurufScore() = welearnRepository.scoreHurufUser()

    override fun angkaHighScore() = welearnRepository.highScoreAngka()

    override fun hurufHighScore() = welearnRepository.highScoreHuruf()

    override fun pushNotification(body: PushNotification) = welearnRepository.pushNotification(body)

    override fun pushNotificationStart(body: PushNotificationStart) = welearnRepository.pushStartNotification(body)

    override fun angkaPredict(
        id_soal: Int,
        image: ArrayList<String>
    ) = welearnRepository.predictAngka(id_soal, image)

    override fun hurufPredict(
        id_soal: Int,
        image: ArrayList<String>
    ) = welearnRepository.predictHuruf(id_soal, image)

    override fun testPredict(input: String) = welearnRepository.predictTest(input)

    override fun makeRoomGame(id_jenis: Int) = welearnRepository.makeRoomGame(id_jenis,)

    override fun joinGame(id_game: String, ) = welearnRepository.joinGame(id_game)

    override fun endGame(id_game: String, ) = welearnRepository.endGame(id_game)

    override fun scoreMulti(id_game: Int, ) = welearnRepository.scoreMulti(id_game)

    override fun predictHurufMulti(
        id_game: Int,
        id_soal: Int,
        image: ArrayList<String>,
        duration: Int,

    ) = welearnRepository.predictHurufMulti(id_game, id_soal, image , duration)

    override fun predictAngkaMulti(
        id_game: Int,id_soal: Int,image: ArrayList<String> , duration: Int,
    ) = welearnRepository.predictAngkaMulti(id_game, id_soal, image, duration)

    override fun getJoinedGame() = welearnRepository.getJoinedGame()

    override fun getLevel(id_level: Int, ) = welearnRepository.getLevel(id_level)
}