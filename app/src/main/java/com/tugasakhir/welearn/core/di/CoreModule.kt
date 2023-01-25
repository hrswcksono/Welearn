package com.tugasakhir.welearn.core.di

import com.tugasakhir.welearn.core.utils.Constants.Companion.BASE_URL_API
import com.tugasakhir.welearn.core.utils.SessionManager
import com.tugasakhir.welearn.data.*
import com.tugasakhir.welearn.data.source.remote.AuthDataSource
import com.tugasakhir.welearn.data.source.remote.MultiPlayerDataSource
import com.tugasakhir.welearn.data.source.remote.SinglePlayerDataSource
import com.tugasakhir.welearn.data.source.remote.network.AuthApi
import com.tugasakhir.welearn.data.source.remote.network.MultiPlayerApi
import com.tugasakhir.welearn.data.source.remote.network.SinglePlayerApi
import com.tugasakhir.welearn.domain.repository.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// jalankan lt --port 8000 --subdomain gfhdgdjsjcbsjcgdjsdjdjs
val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(AuthApi::class.java)
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(MultiPlayerApi::class.java)
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(SinglePlayerApi::class.java)
    }
}

val repositoryModule = module {
    single { AuthDataSource(get<AuthApi>()) }
    single { MultiPlayerDataSource(get<MultiPlayerApi>())}
    single { SinglePlayerDataSource(get<SinglePlayerApi>()) }
    factory { SessionManager(get()) }
    single<IAuthRepository> { AuthRepository(get(), get())}
    single<IMultiPlayerRepository> { MultiPlayerRepository(get(), get())}
    single<ISinglePlayerRepository> { SinglePlayerRepository(get(), get()) }
}