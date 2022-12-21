package com.tugasakhir.welearn.core.utils

import com.tugasakhir.welearn.data.source.local.entity.UserParticipant
import com.tugasakhir.welearn.data.source.remote.response.*
import com.tugasakhir.welearn.domain.entity.*

object DataMapper {
    fun mapperLogin(it: Message) = LoginEntity(
        token = it.token.toString(),
        name = it.name.toString(),
        id = it.id!!
    )

    fun mapperDetailUser(it: DMessage) = ProfileEntity(
        username = it.username.toString(),
        email = it.email.toString(),
        jenisKelamin = it.jenisKelamin.toString(),
        score = it.score.toString(),
        angka = it.angka.toString()
    )

    fun mapperSoal(it: SMessage) = SoalEntity(
        idSoal = it.idSoal!!,
        idJenis = it.idJenis!!,
        idLevel = it.idLevel!!,
        soal = it.soal.toString(),
        keterangan = it.keterangan.toString(),
        jawaban = it.jawaban.toString()
    )

    fun mapScoreUser(it: ScoreResponse) = ScoreEntity(
        score = it.score!!
    )

    fun mapperPredict(it: PredictResponse) = ResultPredictEntity(
        success = it.success.toString(),
        message = it.message.toString(),
        text = it.text.toString()
    )

    fun mapperHighScore(input: List<HighScoreResponse>): List<RankingScoreEntity> {
        val highScoreList = ArrayList<RankingScoreEntity>()
        input.map {
            val highScore = RankingScoreEntity(
                name = it.name,
                total = it.total
            )
            highScoreList.add(highScore)
        }
        return highScoreList
    }

    fun mapperLevel(input: List<LevelResponse>): List<LevelEntity> {
        val levelList = ArrayList<LevelEntity>()
        input.map {
            val level = LevelEntity(
                id = it.id!!,
                idLevel = it.idLevel!!,
                levelSoal = it.levelSoal.toString(),
                idJenis = it.idJenis!!
            )
            levelList.add(level)
        }
        return levelList
    }

    // soal

    fun mapperRandomSoal(input: List<RandomSoalResponse>): List<SoalEntity> {
        val soalList = ArrayList<SoalEntity>()
        input.map {
            val soal = SoalEntity(
                idSoal = it.idSoal,
                idJenis = it.idJenis,
                idLevel = it.idLevel,
                soal = it.soal,
                keterangan = it.keterangan,
                jawaban = it.jawaban
            )
            soalList.add(soal)
        }
        return soalList
    }


    // user participant

    fun mapResponseToEntitiesUserParticipant(input: List<UserParticipatedResponse>): List<UserParticipant> {
        val userList = ArrayList<UserParticipant>()
        input.map {
            val user = UserParticipant(
                id = it.id!!,
                idGame = it.idGame!!,
                username = it.name.toString()
            )
            userList.add(user)
        }
        return userList
    }

    fun mapEntitiesToDomainUserParticipant(input: List<UserParticipant>): List<UserPaticipantEntity> =
        input.map {
            UserPaticipantEntity(
                it.id,
                it.idGame,
                it.username
            )
        }

    fun mapperJoinedGame(input: List<JoinedGameResponse>): List<UserJoinEntity> {
        val joinGameList = ArrayList<UserJoinEntity>()
        input.map{
            val joinGame = UserJoinEntity(
                id = it.idGame.toString(),
                username = it.username.toString(),
                jenisSoal = it.jenis_soal.toString(),
                idLevel = it.id_level!!
            )
            joinGameList.add(joinGame)
        }
        return joinGameList
    }

    fun mapperScoreMulti(input: List<ScoreMultiplayerResponse>): List<ScoreMultiEntity> {
        val scoreMultiList = ArrayList<ScoreMultiEntity>()
        input.map {
            val scoreMulti = ScoreMultiEntity(
                name = it.name.toString(),
                score = it.score!!,
                duration = it.duration!!
            )
            scoreMultiList.add(scoreMulti)
        }
        return scoreMultiList
    }

    fun mapperRegister(input: RegisterResponse): String {
        return input.success.toString()
    }

    fun mapperString(input: SimpleResponse): String {
        return input.message.toString()
    }

}