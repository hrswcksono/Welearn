package com.tugasakhir.welearn.core.data.source.remote.network

import com.tugasakhir.welearn.core.data.source.remote.response.*
import com.tugasakhir.welearn.core.utils.Constants.Companion.CONTENT_TYPE
import com.tugasakhir.welearn.core.utils.Constants.Companion.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // login
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
    @GET("randAngka/{id}")
    suspend fun getRandomAngka(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): RandomResponse

    // random huruf
    @GET("randHuruf/{id}")
    suspend fun getRandomHuruf(
        @Path("id") id: Int,
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

    // soal angka
    @GET("soalAngkabyID")
    suspend fun getSoalAngkabyID(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): SoalResponse

    // soal huruf
    @GET("soalHurufbyID")
    suspend fun getSoalHurufbyID(
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

    @Headers("Authorization: key=$SERVER_KEY",
        "Content-Type:$CONTENT_TYPE")
    @POST
    suspend fun postNotification(
        @Url url: String,
        @Body notification: PushNotification
    ): PushNotificationResponse
}