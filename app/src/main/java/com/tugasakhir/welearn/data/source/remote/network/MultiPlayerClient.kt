package com.tugasakhir.welearn.data.source.remote.network

import com.tugasakhir.welearn.utils.Constants
import com.tugasakhir.welearn.data.source.remote.response.*
import com.tugasakhir.welearn.domain.entity.PushNotification
import retrofit2.http.*

interface MultiPlayerClient {

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

    @Headers("Authorization: key=${Constants.SERVER_KEY}",
        "Content-Type:${Constants.CONTENT_TYPE}")
    @POST
    suspend fun postNotification(
        @Url url: String,
        @Body notification: PushNotification
    ): PushNotificationResponse

    @FormUrlEncoded
    @POST("makeRoom")
    suspend fun makeRoom(
        @Field("id_jenis") idJenis : Int,
        @Field("id_level") idLevel: Int,
        @Header("Authorization") token: String
    ): MakeRoomResponse

    @FormUrlEncoded
    @POST("joinGame")
    suspend fun joinGame(
        @Field("id_room") idRoom : Int,
        @Header("Authorization") token: String
    ): JoinGameResponse

    @FormUrlEncoded
    @POST("forceEndGame")
    suspend fun forceEndGame(
        @Field("id") id : String,
        @Header("Authorization") token: String
    ): ForceEndGameResponse

    @FormUrlEncoded
    @POST("gameAlreadyEnd")
    suspend fun gameAlreadyEnd(
        @Field("id") id : String,
        @Header("Authorization") token: String
    ): GameAlreadyEndResponse

    @GET("showScoreMulti/{id}")
    suspend fun getListScoreMulti(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): ScoreMultiResponse

    @FormUrlEncoded
    @POST("savePredictMulti")
    suspend fun savePredictAngkaMulti(
        @Field("id_game") idGame : Int,
        @Field("id_soal") idSoal : Int,
        @Field("score") score : Int,
        @Field("duration") duration : Int,
        @Header("Authorization") token: String
    ): SavePredictMultiResponse

    @FormUrlEncoded
    @POST("savePredictMulti")
    suspend fun savePredictHurufMulti(
        @Field("id_game") idGame : Int,
        @Field("id_soal") idSoal : Int,
        @Field("score") score : Int,
        @Field("duration") duration : Int,
        @Header("Authorization") token: String
    ): SavePredictMultiResponse

    @GET("joinedGame")
    suspend fun getListUserJoin(
        @Header("Authorization") token: String
    ): ListJoinedGameResponse

    @GET("listJoin/{id}")
    suspend fun getListUserParticipant(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): ListUserParticipatedResponse
}