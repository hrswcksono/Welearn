package com.tugasakhir.welearn.data.di

import com.tugasakhir.welearn.data.MultiPlayerRepository
import com.tugasakhir.welearn.data.source.remote.IMultiplayerDataSource
import com.tugasakhir.welearn.data.source.remote.MultiPlayerDataSource
import com.tugasakhir.welearn.data.source.remote.network.MultiPlayerClient
import com.tugasakhir.welearn.domain.repository.IMultiPlayerRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val multiModule = module {
    fun provideUseApiMultiPlayer(retrofit: Retrofit): MultiPlayerClient {
        return retrofit.create(MultiPlayerClient::class.java)
    }
    single { provideUseApiMultiPlayer(get()) }
    single<IMultiplayerDataSource> { MultiPlayerDataSource(get<MultiPlayerClient>()) }
    single<IMultiPlayerRepository> { MultiPlayerRepository(get<IMultiplayerDataSource>()) }
}