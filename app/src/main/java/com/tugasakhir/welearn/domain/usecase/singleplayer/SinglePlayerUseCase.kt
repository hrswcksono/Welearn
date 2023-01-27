package com.tugasakhir.welearn.domain.usecase.singleplayer

import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface SinglePlayerUseCase {
    fun angkaPredict(idSoal: Int, score: Int, authToken: String): Flow<ResultPredict>
    fun hurufPredict(idSoal: Int, score: Int, authToken: String): Flow<ResultPredict>
    fun getLevel(idLevel: Int, authToken: String): Flow<List<Level>>
    fun getSoalRandomSinglePlayer(jenis: Int, level: Int, authToken: String): Flow<List<Soal>>
    fun userScore(id: Int, authToken: String): Flow<Score>
    fun getHighScore(id: Int, authToken: String): Flow<List<RankingSingleScore>>
}