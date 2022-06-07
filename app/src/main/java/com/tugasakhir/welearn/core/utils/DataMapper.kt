package com.tugasakhir.welearn.core.utils

import com.tugasakhir.welearn.core.data.source.remote.response.*
import com.tugasakhir.welearn.domain.model.*

object DataMapper {
    fun mapperLoginToken(it: Message) = Login(
        token = it.token.toString(),
        name = it.name.toString()
    )

    fun mapperDetailUser(it: DMessage) = User(
        username = it.username.toString(),
        email = it.email.toString(),
        jenis_kelamin = it.jenisKelamin.toString(),
        score = it.score.toString(),
        angka = it.angka.toString()
    )

    fun mapperSoal(it: SMessage) = Soal(
        id_soal = it.idSoal!!,
        id_jenis_soal = it.idJenis!!,
        id_level = it.idLevel!!,
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
                id_soal = it.idSoal,
                id_jenis_soal = it.idJenis,
                id_level = it.idLevel,
                soal = it.soal,
                keterangan = it.keterangan,
                jawaban = it.jawaban
            )
            soalList.add(soal)
        }
        return soalList
    }

    fun mapperHighScore(input: List<HMessageItem>): List<UserScore> {
        val highScoreList = ArrayList<UserScore>()
        input.map {
            val highScore = UserScore(
                name = it.name,
                total = it.total
            )
            highScoreList.add(highScore)
        }
        return highScoreList
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
        var result: String = ""
        result = input.success.toString()
        return result
    }

    fun mapperString(input: SimpleResponse): String {
        var result = ""
        result = input.message.toString()
        return result
    }

    fun mapperRoom(input: MakeRoomResponse): String {
        var result = ""
        result = input.message!!.id.toString()
        return result
    }


}