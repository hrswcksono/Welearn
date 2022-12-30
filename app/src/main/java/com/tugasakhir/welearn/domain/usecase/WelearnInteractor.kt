package com.tugasakhir.welearn.domain.usecase

import com.tugasakhir.welearn.domain.entity.*
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

    override fun getSoalRandomSinglePlayer(jenis: Int, level: Int) = welearnRepository.getRandSoalSingle(jenis, level)

    override fun getIDSoalMultiplayer(jenis: Int, level: Int) = welearnRepository.getIDSoalMultiplayer(jenis, level)

    override fun getSoalByID(id: Int) = welearnRepository.soalByID(id)

    override fun userScore(id: Int) = welearnRepository.scoreUser(id)

    override fun getHighScore(id: Int) = welearnRepository.highScore(id)

    override fun pushNotification(body: PushNotification) = welearnRepository.pushNotification(body)

    override fun angkaPredict(
        idSoal: Int,
        score: Int
    ) = welearnRepository.predictAngka(idSoal, score)

    override fun hurufPredict(
        idSoal: Int,
        score: Int
    ) = welearnRepository.predictHuruf(idSoal, score)

    override fun makeRoomGame(idJenis: Int, idLevel: Int) = welearnRepository.makeRoomGame(idJenis, idLevel)

    override fun joinGame(idGame: String) = welearnRepository.joinGame(idGame)

    override fun endGame(idGame: String) = welearnRepository.endGame(idGame)

    override fun scoreMulti(idGame: Int) = welearnRepository.scoreMulti(idGame)

    override fun predictHurufMulti(
        idGame: Int,
        idSoal: Int,
        score: Int,
        duration: Int,
    ) = welearnRepository.predictHurufMulti(idGame, idSoal, score , duration)

    override fun predictAngkaMulti(
        idGame: Int,idSoal: Int,score: Int , duration: Int,
    ) = welearnRepository.predictAngkaMulti(idGame, idSoal, score, duration)

    override fun userJoinedGame() = welearnRepository.getJoinedGame()

    override fun getLevel(idLevel: Int) = welearnRepository.getLevel(idLevel)
    override fun getUserParticipant(idGame: Int) = welearnRepository.getUserParticipant(idGame)
}