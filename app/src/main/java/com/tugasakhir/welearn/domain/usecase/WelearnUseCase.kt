package com.tugasakhir.welearn.domain.usecase

import com.tugasakhir.welearn.domain.model.*
import kotlinx.coroutines.flow.Flow

interface WelearnUseCase {
    fun userLogin(username: String, password: String) : Flow<Login>
    fun userDetail(token: String) : Flow<User>
    fun userRegister(username: String,
                     password: String,
                     email: String,
                     name: String,
                     jenis_kelamin: String): String
//    fun angkaRandom(level: String, token: String): Flow<ListRandomSoal>
//    fun hurufRandom(level: String, token: String): Flow<ListRandomSoal>
//    fun angkaScore(token: String): Flow<Score>
//    fun hurufScore(token: String): Flow<Score>
//    fun angkaHighScore(token: String): Flow<HighScore>
//    fun hurufHighScore(token: String): Flow<HighScore>
}