package com.tugasakhir.welearn.di

import com.tugasakhir.welearn.core.data.WelearnRepository
import com.tugasakhir.welearn.core.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.core.data.source.remote.network.ApiService
import com.tugasakhir.welearn.core.utils.AppExecutors
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
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
            .baseUrl("https://gfhdgdjsjcbsjcgdjsdjdjs.loca.lt/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IWelearnRepository> { WelearnRepository(get())}
}