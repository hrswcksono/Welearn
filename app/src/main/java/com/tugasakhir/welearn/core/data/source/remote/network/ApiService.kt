package com.tugasakhir.welearn.core.data.source.remote.network

import com.tugasakhir.welearn.core.data.source.remote.response.*
import retrofit2.http.*

interface ApiService {

    // login
//    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse

    // register
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("jenis_kelamin") jenis_kelamin: String,
    ): RegisterResponse

    // logout
    @GET("logout")
    suspend fun logout(@Header("Authorization") token: String): LogoutResponse

    // detail
    @GET("detail")
    suspend fun detail(@Header("Authorization") token: String): DetailResponse

    // random angka
    @GET("randAngka")
    suspend fun getRandomAngka(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): RandomResponse

    // random huruf
    @GET("randHuruf")
    suspend fun getRandomHuruf(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): RandomResponse

    // soal angka
    @GET("soalAngka")
    suspend fun getSoalAngka(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): SoalResponse

    // soal huruf
    @GET("soalHuruf")
    suspend fun getSoalHuruf(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): SoalResponse

    // highscore huruf
    @GET("scoreTHuruf")
    suspend fun getHighScoreHuruf(
        @Header("Authorization") token: String
    ) : HighScoreResponse

    // highscore angka
    @GET("scoreTAngka")
    suspend fun getHighScoreAngka(
        @Header("Authorization") token: String
    ) : HighScoreResponse
}