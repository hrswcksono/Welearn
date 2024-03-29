package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface ISinglePlayerRepository {
    fun scoreUser(id: Int, authToken: String): Flow<Score>
    fun highScore(id: Int, authToken: String): Flow<List<RankingSingleScore>>
    fun predictAngka(idSoal: Int, score: Int, authToken: String): Flow<ResultPredict>
    fun predictHuruf(idSoal: Int, score: Int, authToken: String): Flow<ResultPredict>
    fun getLevel(idLevel: Int, authToken: String): Flow<List<Level>>
    fun getRandSoalSingle(jenis: Int ,level: Int, authToken: String): Flow<List<Soal>>
}