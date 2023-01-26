package com.tugasakhir.welearn.core.di

import com.tugasakhir.welearn.core.utils.Constants.Companion.BASE_URL_API
import com.tugasakhir.welearn.data.*
import com.tugasakhir.welearn.data.source.remote.*
import com.tugasakhir.welearn.data.source.remote.network.AuthClient
import com.tugasakhir.welearn.data.source.remote.network.MultiPlayerClient
import com.tugasakhir.welearn.data.source.remote.network.SinglePlayerClient
import com.tugasakhir.welearn.domain.repository.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

// jalankan lt --port 8000 --subdomain gfhdgdjsjcbsjcgdjsdjdjs
val networkModule = module {
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    single {
        provideClient()
    }
    single {
        provideRetrofit(get())
    }
}

val apiModule = module {
    fun provideUseApiAuth(retrofit: Retrofit): AuthClient {
        return retrofit.create(AuthClient::class.java)
    }
    fun provideUseApiMultiPlayer(retrofit: Retrofit): MultiPlayerClient {
        return retrofit.create(MultiPlayerClient::class.java)
    }
    fun provideUseApiSinglePlayer(retrofit: Retrofit): SinglePlayerClient {
        return retrofit.create(SinglePlayerClient::class.java)
    }

    single { provideUseApiAuth(get()) }
    single { provideUseApiMultiPlayer(get()) }
    single { provideUseApiSinglePlayer(get()) }
}

val dataSourceModule = module {
    single<IAuthDataSource> { AuthDataSource(get<AuthClient>()) }
    single<IMultiplayerDataSource> { MultiPlayerDataSource(get<MultiPlayerClient>())}
    single<ISinglePlayerDataSource> { SinglePlayerDataSource(get<SinglePlayerClient>()) }
}

val repositoryModule = module {
    single<IAuthRepository> { AuthRepository(get())}
    single<IMultiPlayerRepository> { MultiPlayerRepository(get())}
    single<ISinglePlayerRepository> { SinglePlayerRepository(get()) }
}


