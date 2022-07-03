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
        jenisKelamin: String
    ) = welearnRepository.registerUser(username, password, email, name, jenisKelamin)

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

//    override fun pushNotificationStart(body: PushNotificationStart) = welearnRepository.pushStartNotification(body)

    override fun angkaPredict(
        idSoal: Int,
        image: ArrayList<String>
    ) = welearnRepository.predictAngka(idSoal, image)

    override fun hurufPredict(
        idSoal: Int,
        image: ArrayList<String>
    ) = welearnRepository.predictHuruf(idSoal, image)

    override fun testPredict(input: String) = welearnRepository.predictTest(input)

    override fun makeRoomGame(idJenis: Int) = welearnRepository.makeRoomGame(idJenis)

    override fun joinGame(idGame: String) = welearnRepository.joinGame(idGame)

    override fun endGame(idGame: String) = welearnRepository.endGame(idGame)

    override fun scoreMulti(idGame: Int) = welearnRepository.scoreMulti(idGame)

    override fun predictHurufMulti(
        idGame: Int,
        idSoal: Int,
        image: ArrayList<String>,
        duration: Int,

    ) = welearnRepository.predictHurufMulti(idGame, idSoal, image , duration)

    override fun predictAngkaMulti(
        idGame: Int,idSoal: Int,image: ArrayList<String> , duration: Int,
    ) = welearnRepository.predictAngkaMulti(idGame, idSoal, image, duration)

    override fun userJoinedGame() = welearnRepository.getJoinedGame()

    override fun getLevel(idLevel: Int) = welearnRepository.getLevel(idLevel)
}