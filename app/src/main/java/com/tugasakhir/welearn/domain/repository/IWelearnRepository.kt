package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.domain.model.*
import kotlinx.coroutines.flow.Flow

interface IWelearnRepository {
    fun loginUser(username: String, password: String): Flow<Login>
    fun detailUser(token: String): Flow<User>
    fun registerUser(username: String,
                     password: String,
                     email: String,
                     name: String,
                     jenis_kelamin: String): Flow<String>
    fun logoutUser(token: String) : Flow<String>
    fun randAngka(level: Int, token: String): Flow<List<Soal>>
    fun randHuruf(level: Int, token: String): Flow<List<Soal>>
    fun soalAngkaMultiplayer(level: Int, token: String): Flow<Soal>
    fun soalHurufMultiplayer(level: Int, token: String): Flow<Soal>
    fun soalAngkaByID(id:Int, token: String): Flow<Soal>
    fun soalHurufByID(id: Int, token: String): Flow<Soal>
    fun scoreAngka(token: String): Flow<Score>
    fun scoreHuruf(token: String): Flow<Score>
    fun highScoreAngka(token: String): Flow<List<UserScore>>
    fun highScoreHuruf(token: String): Flow<List<UserScore>>
}