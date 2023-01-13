package com.tugasakhir.welearn.core.di

import androidx.room.Room
import com.tugasakhir.welearn.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.data.source.remote.network.ApiService
import com.tugasakhir.welearn.core.utils.Constants.Companion.BASE_URL_API
import com.tugasakhir.welearn.core.utils.SessionManager
import com.tugasakhir.welearn.data.*
import com.tugasakhir.welearn.data.source.ScoreRepository
import com.tugasakhir.welearn.domain.repository.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
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
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    factory { SessionManager(get()) }
//    single<IWelearnRepository> { WelearnRepository(get())}
    single<IMultiPlayerRepository> { MultiPlayerRepository(get(), get())}
    single<IScoreRepository> { ScoreRepository(get(), SessionManager(get()))}
    single<ISinglePlayerRepository> { SinglePlayerRepository(get(), SessionManager(get()))}
    single<ISoalRepsoitory> { SoalRepository(get(), SessionManager(get()))}
    single<IUserRepository> { UserRepository(get(), SessionManager(get()))}
}