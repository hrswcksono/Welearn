package com.tugasakhir.welearn.data.di

import com.tugasakhir.welearn.data.AuthRepository
import com.tugasakhir.welearn.data.source.remote.AuthDataSource
import com.tugasakhir.welearn.data.source.remote.IAuthDataSource
import com.tugasakhir.welearn.data.source.remote.network.AuthClient
import com.tugasakhir.welearn.domain.repository.IAuthRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {
    fun provideUseApiAuth(retrofit: Retrofit): AuthClient {
        return retrofit.create(AuthClient::class.java)
    }
    single { provideUseApiAuth(get()) }
    single<IAuthDataSource> { AuthDataSource(get<AuthClient>()) }
    single<IAuthRepository> { AuthRepository(get<IAuthDataSource>()) }
}