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
                     jenis_kelamin: String): Flow<String>
    fun userLogout(token: String): Flow<String>
    fun angkaRandom(level: Int, token: String): Flow<List<Soal>>
    fun hurufRandom(level: Int, token: String): Flow<List<Soal>>
//    fun angkaScore(token: String): Flow<Score>
//    fun hurufScore(token: String): Flow<Score>
//    fun angkaHighScore(token: String): Flow<HighScore>
//    fun hurufHighScore(token: String): Flow<HighScore>
}