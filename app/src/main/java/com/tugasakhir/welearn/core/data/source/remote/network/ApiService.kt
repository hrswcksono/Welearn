package com.tugasakhir.welearn.core.data.source.remote.network

import com.tugasakhir.welearn.core.data.source.remote.response.*
import com.tugasakhir.welearn.core.utils.Constants.Companion.CONTENT_TYPE
import com.tugasakhir.welearn.core.utils.Constants.Companion.SERVER_KEY
import com.tugasakhir.welearn.domain.model.PushNotification
import com.tugasakhir.welearn.domain.model.PushNotificationStart
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
    suspend fun logout(
        @Header("Authorization") token: String
    ): SimpleResponse

    // detail
    @GET("detail")
    suspend fun detail(
        @Header("Authorization") token: String
    ): DetailResponse

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
    @GET("soalAngka/{id}")
    suspend fun getSoalAngkaRandom(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): SimpleResponse

    // soal huruf
    @GET("soalHuruf/{id}")
    suspend fun getSoalHurufRandom(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): SimpleResponse

//    // soal angka
//    @GET("soalAngkabyID/{id}")
//    suspend fun getSoalAngkabyID(
//        @Path("id") id: Int,
//        @Header("Authorization") token: String
//    ): SoalResponse

    @GET("soalbyID/{id}")
    suspend fun getSoalByID(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): SoalResponse

//    // soal huruf
//    @GET("soalHurufbyID/{id}")
//    suspend fun getSoalHurufbyID(
//        @Path("id") id: Int,
//        @Header("Authorization") token: String
//    ): SoalResponse

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

    @GET("scoreHurufUser")
    suspend fun scoreHurufUser(
        @Header("Authorization") token: String
    ) : ScoreResponse

    @GET("scoreAngkaUser")
    suspend fun scoreAngkaUser(
        @Header("Authorization") token: String
    ) : ScoreResponse

    @Headers("Authorization: key=$SERVER_KEY",
        "Content-Type:$CONTENT_TYPE")
    @POST
    suspend fun postNotification(
        @Url url: String,
        @Body notification: PushNotification
    ): PushNotificationResponse

    @Headers("Authorization: key=$SERVER_KEY",
        "Content-Type:$CONTENT_TYPE")
    @POST
    suspend fun postStartGame(
        @Url url: String,
        @Body notification: PushNotificationStart
    ): PushNotificationResponse

    @FormUrlEncoded
    @POST("predictangka")
    suspend fun predictAngka(
        @Field("id_soal") id_soal : String,
        @Field("img[]") image : ArrayList<String>,
        @Header("Authorization") token: String
    ): PredictResponse

    @FormUrlEncoded
    @POST("predict")
    suspend fun predictHuruf(
        @Field("id_soal") id_soal : String,
        @Field("img[]") image : ArrayList<String>,
        @Header("Authorization") token: String
    ): PredictResponse

    @FormUrlEncoded
    @POST("test")
    suspend fun test(
        @Field("input") input : String,
        @Header("Authorization") token: String
    ): PredictResponse

    @GET("makeRoom")
    suspend fun makeRoom(
        @Header("Authorization") token: String
    ): SimpleResponse

    @FormUrlEncoded
    @POST("joinGame")
    suspend fun joinGame(
        @Field("id_game") id_game : Int,
        @Header("Authorization") token: String
    ): SimpleResponse

    @FormUrlEncoded
    @POST("endGame")
    suspend fun endGame(
        @Field("id") id : String,
        @Header("Authorization") token: String
    ): SimpleResponse

    @GET("showScoreMulti/{id}")
    suspend fun scoreMulti(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): ScoreMultiplayerResponse

    @FormUrlEncoded
    @POST("predictAngkaMulti")
    suspend fun predictAngkaMulti(
        @Field("id_game") id_game : Int,
        @Field("id_jenis") id_jenis : Int,
        @Field("img[]") image : ArrayList<String>,
        @Header("Authorization") token: String
    ): SimpleResponse

    @FormUrlEncoded
    @POST("predictHurufMulti")
    suspend fun predictHurufMulti(
        @Field("id_game") id_game : Int,
        @Field("id_jenis") id_jenis : Int,
        @Field("img[]") image : ArrayList<String>,
        @Header("Authorization") token: String
    ): SimpleResponse

}