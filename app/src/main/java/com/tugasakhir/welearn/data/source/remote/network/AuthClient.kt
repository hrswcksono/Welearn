package com.tugasakhir.welearn.data.source.remote.network

import com.tugasakhir.welearn.data.source.remote.response.DetailResponse
import com.tugasakhir.welearn.data.source.remote.response.LoginResponse
import com.tugasakhir.welearn.data.source.remote.response.RegisterResponse
import com.tugasakhir.welearn.data.source.remote.response.SimpleResponse
import retrofit2.http.*

interface AuthClient {
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
}