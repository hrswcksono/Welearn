package com.tugasakhir.welearn.core.data.source.remote.network

import com.tugasakhir.welearn.core.data.source.remote.response.*
import com.tugasakhir.welearn.core.utils.Constants.Companion.CONTENT_TYPE
import com.tugasakhir.welearn.core.utils.Constants.Companion.SERVER_KEY
import com.tugasakhir.welearn.domain.entity.PushNotification
//import com.tugasakhir.welearn.domain.model.PushNotificationStart
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
        @Field("jenis_kelamin") jenisKelamin: String,
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

    // random soal
    @GET("randListSoal/{jenis}/{level}")
    suspend fun getRandomSoal(
        @Path("jenis") jenis: Int,
        @Path("level") level: Int,
        @Header("Authorization") token: String
    ): RandomResponse

    // soal angka
    @GET("getIDSoalMulti/{jenis}/{level}")
    suspend fun getIDSoalMulti(
        @Path("jenis") jenis: Int,
        @Path("level") level: Int,
        @Header("Authorization") token: String
    ): SimpleResponse

    @GET("soalbyID/{id}")
    suspend fun getSoalByID(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): SoalResponse

    // highscore
    @GET("highscore/{id}")
    suspend fun getHighScore(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ) : HighScoreResponse

    @GET("scoreUser/{id}")
    suspend fun scoreUser(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ) : ScoreResponse

    @Headers("Authorization: key=$SERVER_KEY",
        "Content-Type:$CONTENT_TYPE")
    @POST
    suspend fun postNotification(
        @Url url: String,
        @Body notification: PushNotification
    ): PushNotificationResponse

    @FormUrlEncoded
    @POST("predictangka")
    suspend fun predictAngka(
        @Field("id_soal") idSoal : Int,
        @Field("img[]") image : ArrayList<String>,
        @Header("Authorization") token: String
    ): PredictResponse

    @FormUrlEncoded
    @POST("predicthuruf")
    suspend fun predictHuruf(
        @Field("id_soal") idSoal : Int,
        @Field("img[]") image : ArrayList<String>,
        @Header("Authorization") token: String
    ): PredictResponse

    @FormUrlEncoded
    @POST("makeRoom")
    suspend fun makeRoom(
        @Field("id_jenis") idJenis : Int,
        @Header("Authorization") token: String
    ): SimpleResponse

    @FormUrlEncoded
    @POST("joinGame")
    suspend fun joinGame(
        @Field("id_game") idGame : Int,
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
        @Field("id_game") idGame : Int,
        @Field("id_soal") idSoal : Int,
        @Field("img[]") image : ArrayList<String>,
        @Field("duration") duration : Int,
        @Header("Authorization") token: String
    ): SimpleResponse

    @FormUrlEncoded
    @POST("predictHurufMulti")
    suspend fun predictHurufMulti(
        @Field("id_game") idGame : Int,
        @Field("id_soal") idSoal : Int,
        @Field("img[]") image : ArrayList<String>,
        @Field("duration") duration : Int,
        @Header("Authorization") token: String
    ): SimpleResponse

    @GET("joinedGame")
    suspend fun joinedUser(
        @Header("Authorization") token: String
    ): JoinedGameResponse

    @GET("level/{id}")
    suspend fun getLevel(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): LevelResponse
}