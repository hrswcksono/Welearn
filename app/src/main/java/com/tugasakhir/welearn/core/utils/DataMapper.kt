package com.tugasakhir.welearn.core.utils

import com.tugasakhir.welearn.core.data.source.remote.response.*
import com.tugasakhir.welearn.domain.model.*

object DataMapper {
    fun mapperLogin(it: Message) = Login(
        token = it.token.toString(),
        name = it.name.toString(),
        id = it.id!!
    )

    fun mapperDetailUser(it: DMessage) = User(
        username = it.username.toString(),
        email = it.email.toString(),
        jenisKelamin = it.jenisKelamin.toString(),
        score = it.score.toString(),
        angka = it.angka.toString()
    )

    fun mapperSoal(it: SMessage) = Soal(
        idSoal = it.idSoal!!,
        idJenisSoal = it.idJenis!!,
        idLevel = it.idLevel!!,
        soal = it.soal.toString(),
        keterangan = it.keterangan.toString(),
        jawaban = it.jawaban.toString()
    )

    fun mapScoreUser(it: ScoreMessage) = Score(
        score = it.score!!
    )

    fun mapperPredict(it: PredictResponse) = ResultPredict(
        success = it.success.toString(),
        message = it.message.toString(),
        text = it.text.toString()
    )

    fun mapperRandomSoal(input: List<MessageItem>): List<Soal> {
        val soalList = ArrayList<Soal>()
        input.map {
            val soal = Soal(
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

    fun mapperHighScore(input: List<HMessageItem>): List<RankingScore> {
        val highScoreList = ArrayList<RankingScore>()
        input.map {
            val highScore = RankingScore(
                name = it.name,
                total = it.total
            )
            highScoreList.add(highScore)
        }
        return highScoreList
    }

    fun mapperLevel(input: List<MessageLevel>): List<Level> {
        val levelList = ArrayList<Level>()
        input.map {
            val level = Level(
                idLevel = it.idLevel!!,
                levelSoal = it.levelSoal.toString()
            )
            levelList.add(level)
        }
        return levelList
    }

    fun mapperJoinedGame(input: List<JGameResponse>): List<UserJoin> {
        val joinGameList = ArrayList<UserJoin>()
        input.map{
            val joinGame = UserJoin(
                id = it.idGame.toString(),
                username = it.username.toString(),
                jenisSoal = it.jenis_soal.toString()
            )
            joinGameList.add(joinGame)
        }
        return joinGameList
    }

    fun mapperScoreMulti(input: List<ScoreMultiItem>): List<ScoreMulti> {
        val scoreMultiList = ArrayList<ScoreMulti>()
        input.map {
            val scoreMulti = ScoreMulti(
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