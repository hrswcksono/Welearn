package com.tugasakhir.welearn.utils

import com.tugasakhir.welearn.data.source.remote.response.*
import com.tugasakhir.welearn.domain.entity.*
import com.tugasakhir.welearn.domain.entity.Room

object DataMapper {

    fun mapperGameAlreadyEnd(it: GameAlreadyEndResponse): String {
        return it.data!!
    }

    fun mapperForcedEndGame(it: ForceEndGameResponse): String {
        return it.data!!
    }

    fun mapperPredictMulti(it: SavePredictMultiResponse) = SavePredictMulti(
        status = it.data!!
    )

    fun mapperJoinGame(it: JoinGameResponse) = JoinGame(
        status = it.data!!
    )

    fun mapperRoom(it:RoomResponse) = Room(
        id_room = it.idRoom!!,
        id_game = it.idGame!!
    )

    fun mapperLogin(it: Message) = Login(
        token = it.token.toString(),
        name = it.name.toString(),
        id = it.id!!
    )

    fun mapperDetailUser(it: DMessage) = Profile(
        username = it.username.toString(),
        email = it.email.toString(),
        jenisKelamin = it.jenisKelamin.toString(),
        score = it.score.toString(),
        angka = it.angka.toString()
    )

    fun mapperSoal(it: SoalResponseMessage) = Soal(
        idSoal = it.idSoal!!,
        idJenis = it.idJenis!!,
        idLevel = it.idLevel!!,
        soal = it.soal.toString(),
        keterangan = it.keterangan.toString(),
        jawaban = it.jawaban.toString()
    )

    fun mapScoreUser(it: ScoreResponse) = Score(
        score = it.score!!
    )

    fun mapperPredict(it: PredictResponse) = ResultPredict(
        success = it.success.toString(),
        message = it.message.toString(),
        text = it.text.toString()
    )

    fun mapperHighScore(input: List<HighScoreResponse>): List<RankingSingleScore> {
        val highScoreList = ArrayList<RankingSingleScore>()
        input.map {
            val highScore = RankingSingleScore(
                name = it.name,
                total = it.total
            )
            highScoreList.add(highScore)
        }
        return highScoreList
    }

    fun mapperLevel(input: List<LevelResponse>): List<Level> {
        val levelList = ArrayList<Level>()
        input.map {
            val level = Level(
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

    fun mapperRandomSoal(input: List<RandomSoalResponse>): List<Soal> {
        val soalList = ArrayList<Soal>()
        input.map {
            val soal = Soal(
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

    fun mapperUserParticipant(input: List<UserParticipatedResponse>): List<UserPaticipantMulti> {
        val userParticipantL = ArrayList<UserPaticipantMulti>()
        input.map {
            val userParticipant =  UserPaticipantMulti(
                idGame = it.idGame!!,
                id = it.id!!,
                username = it.id.toString()

            )
            userParticipantL.add(userParticipant)
        }
        return userParticipantL
    }

    fun mapperJoinedGame(input: List<JoinedGameResponse>): List<UserJoinMulti> {
        val joinGameList = ArrayList<UserJoinMulti>()
        input.map{
            val joinGame = UserJoinMulti(
                id = it.idGame.toString(),
                username = it.username.toString(),
                jenisSoal = it.jenis_soal.toString(),
                idLevel = it.id_level!!
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
                score = it.score!!.toInt(),
                duration = it.duration!!.toInt()
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

    fun mapIDSoalMulti(input: SimpleResponse) = IDSoalMulti(
        id_soal = input.message.toString()
    )

}