package com.tugasakhir.welearn.core.utils

import com.tugasakhir.welearn.core.data.source.remote.response.*
import com.tugasakhir.welearn.domain.model.*
import org.koin.ext.scope

object DataMapper {
    fun mapperLoginToken(it: Message) = Login(
        token = it.token.toString()
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

    fun mapperRegister(input: RegisterResponse): String {
        var result: String = ""
        result = input.success.toString()
        return result
    }

    fun mapperLogout(input: LogoutResponse): String {
        var result: String = ""
        result = input.message.toString()
        return result
    }


}