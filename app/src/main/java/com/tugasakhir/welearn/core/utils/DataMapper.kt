package com.tugasakhir.welearn.core.utils

import com.tugasakhir.welearn.core.data.source.remote.response.*
import com.tugasakhir.welearn.domain.entity.*

object DataMapper {
    fun mapperLogin(it: Message) = LoginEntity(
        token = it.token.toString(),
        name = it.name.toString(),
        id = it.id!!
    )

    fun mapperDetailUser(it: DMessage) = UserEntity(
        username = it.username.toString(),
        email = it.email.toString(),
        jenisKelamin = it.jenisKelamin.toString(),
        score = it.score.toString(),
        angka = it.angka.toString()
    )

    fun mapperSoal(it: SMessage) = SoalEntity(
        idSoal = it.idSoal!!,
        idJenisSoal = it.idJenis!!,
        idLevel = it.idLevel!!,
        soal = it.soal.toString(),
        keterangan = it.keterangan.toString(),
        jawaban = it.jawaban.toString()
    )

    fun mapScoreUser(it: ScoreMessage) = ScoreEntity(
        score = it.score!!
    )

    fun mapperPredict(it: PredictResponse) = ResultPredictEntity(
        success = it.success.toString(),
        message = it.message.toString(),
        text = it.text.toString()
    )

    fun mapperRandomSoal(input: List<MessageItem>): List<SoalEntity> {
        val soalList = ArrayList<SoalEntity>()
        input.map {
            val soal = SoalEntity(
                idSoal = it.idSoal,
                idJenisSoal = it.idJenis,
                idLevel = it.idLevel,
                soal = it.soal,
                keterangan = it.keterangan,
                jawaban = it.jawaban
            )
            soalList.add(soal)
        }
        return soalList
    }

    fun mapperHighScore(input: List<HMessageItem>): List<RankingScoreEntity> {
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

    fun mapperLevel(input: List<MessageLevel>): List<LevelEntity> {
        val levelList = ArrayList<LevelEntity>()
        input.map {
            val level = LevelEntity(
                idLevel = it.idLevel!!,
                levelSoal = it.levelSoal.toString()
            )
            levelList.add(level)
        }
        return levelList
    }

    fun mapperJoinedGame(input: List<JGameResponse>): List<UserJoinEntity> {
        val joinGameList = ArrayList<UserJoinEntity>()
        input.map{
            val joinGame = UserJoinEntity(
                id = it.idGame.toString(),
                username = it.username.toString(),
                jenisSoal = it.jenis_soal.toString()
            )
            joinGameList.add(joinGame)
        }
        return joinGameList
    }

    fun mapperScoreMulti(input: List<ScoreMultiItem>): List<ScoreMultiEntity> {
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
        var result = ""
        result = input.success.toString()
        return result
    }

    fun mapperString(input: SimpleResponse): String {
        var result = ""
        result = input.message.toString()
        return result
    }


}