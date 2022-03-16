package com.tugasakhir.welearn.core.data.source.remote.network

import com.tugasakhir.welearn.core.data.source.remote.response.LoginResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("signupNewUser?key=AIzaSyAV7wrlD-aII7AlrBGU9lEpAn_2iKqXVuk")
    suspend fun login(
        @Body params: RequestBody
    ): LoginResponse

    @POST
    suspend fun register()

    @POST
    suspend fun create()

    @GET
    suspend fun read()

    @PATCH
    suspend fun update()

    @DELETE
    suspend fun delete()
}