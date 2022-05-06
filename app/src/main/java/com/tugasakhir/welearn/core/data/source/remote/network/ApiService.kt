package com.tugasakhir.welearn.core.data.source.remote.network

import com.tugasakhir.welearn.core.data.source.remote.response.*
import com.tugasakhir.welearn.core.utils.Constants.Companion.BASE_URL_API
import com.tugasakhir.welearn.core.utils.Constants.Companion.CONTENT_TYPE
import com.tugasakhir.welearn.core.utils.Constants.Companion.FCM_BASE_URL
import com.tugasakhir.welearn.core.utils.Constants.Companion.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // login
    @FormUrlEncoded
    @POST("$BASE_URL_API/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse

    // register
    @FormUrlEncoded
    @POST("$BASE_URL_API/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("jenis_kelamin") jenis_kelamin: String,
    ): RegisterResponse

    // logout
    @GET("$BASE_URL_API/logout")
    suspend fun logout(@Header("Authorization") token: String): LogoutResponse

    // detail
    @GET("$BASE_URL_API/detail")
    suspend fun detail(@Header("Authorization") token: String): DetailResponse

    // random angka
    @GET("$BASE_URL_API/randAngka")
    suspend fun getRandomAngka(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): RandomResponse

    // random huruf
    @GET("$BASE_URL_API/randHuruf")
    suspend fun getRandomHuruf(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): RandomResponse

    // soal angka
    @GET("$BASE_URL_API/soalAngka")
    suspend fun getSoalAngka(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): SoalResponse

    // soal huruf
    @GET("s$BASE_URL_API/oalHuruf")
    suspend fun getSoalHuruf(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): SoalResponse

    // highscore huruf
    @GET("$BASE_URL_API/scoreTHuruf")
    suspend fun getHighScoreHuruf(
        @Header("Authorization") token: String
    ) : HighScoreResponse

    // highscore angka
    @GET("$BASE_URL_API/scoreTAngka")
    suspend fun getHighScoreAngka(
        @Header("Authorization") token: String
    ) : HighScoreResponse

    @Headers("Authorization: key=$SERVER_KEY",
        "Content-Type:$CONTENT_TYPE")
    @POST("$FCM_BASE_URL/fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}