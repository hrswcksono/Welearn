package com.tugasakhir.welearn.data.source.remote.network

import com.tugasakhir.welearn.data.source.remote.response.*
import retrofit2.http.*

interface SinglePlayerApi {

    // random soal
    @GET("randListSoal/{jenis}/{level}")
    suspend fun getRandomSoal(
        @Path("jenis") jenis: Int,
        @Path("level") level: Int,
        @Header("Authorization") token: String
    ): ListSoalRandomResponse

    // highscore
    @GET("highscore/{id}")
    suspend fun getHighScore(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ) : ListHighScoreResponse

    @GET("scoreUser/{id}")
    suspend fun scoreUser(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ) : ListScoreResponse

    @FormUrlEncoded
    @POST("predictangka")
    suspend fun predictAngka(
        @Field("id_soal") idSoal : Int,
        @Field("score") score : Int,
        @Header("Authorization") token: String
    ): PredictResponse

    @FormUrlEncoded
    @POST("predicthuruf")
    suspend fun predictHuruf(
        @Field("id_soal") idSoal : Int,
        @Field("score") score : Int,
        @Header("Authorization") token: String
    ): PredictResponse

    @GET("level/{id}")
    suspend fun getLevel(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): ListLevelResponse
}