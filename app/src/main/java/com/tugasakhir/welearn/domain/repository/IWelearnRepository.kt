package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.core.data.source.remote.response.LoginResponse
import com.tugasakhir.welearn.domain.model.*
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface IWelearnRepository {
    fun loginUser(username: String, password: String): Flow<Login>
    fun detailUser(token: String): Flow<User>
    fun randAngka(level: String, token: String): Flow<ListRandomSoal>
    fun randHuruf(level: String, token: String): Flow<ListRandomSoal>
    fun scoreAngka(token: String): Flow<Score>
    fun scoreHuruf(token: String): Flow<Score>
    fun highScoreAngka(token: String): Flow<HighScore>
    fun highScoreHuruf(token: String): Flow<HighScore>
}