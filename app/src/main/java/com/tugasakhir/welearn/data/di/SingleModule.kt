package com.tugasakhir.welearn.data.di

import com.tugasakhir.welearn.data.SinglePlayerRepository
import com.tugasakhir.welearn.data.source.remote.ISinglePlayerDataSource
import com.tugasakhir.welearn.data.source.remote.SinglePlayerDataSource
import com.tugasakhir.welearn.data.source.remote.network.SinglePlayerClient
import com.tugasakhir.welearn.domain.repository.ISinglePlayerRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val singleModule = module {
    fun provideUseApiSinglePlayer(retrofit: Retrofit): SinglePlayerClient {
        return retrofit.create(SinglePlayerClient::class.java)
    }
    single { provideUseApiSinglePlayer(get()) }
    single<ISinglePlayerDataSource> { SinglePlayerDataSource(get<SinglePlayerClient>()) }
    single<ISinglePlayerRepository> { SinglePlayerRepository(get<ISinglePlayerDataSource>()) }
}