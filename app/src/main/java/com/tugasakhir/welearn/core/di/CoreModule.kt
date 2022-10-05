package com.tugasakhir.welearn.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.tugasakhir.welearn.core.data.WelearnRepository
import com.tugasakhir.welearn.core.data.source.local.LocalDataSource
import com.tugasakhir.welearn.core.data.source.local.room.WelearnDatabase
import com.tugasakhir.welearn.core.data.source.remote.RemoteDataSource
import com.tugasakhir.welearn.core.data.source.remote.network.ApiService
import com.tugasakhir.welearn.core.utils.AppExecutors
import com.tugasakhir.welearn.core.utils.Constants.Companion.BASE_URL_API
import com.tugasakhir.welearn.core.utils.SharedPreference
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<WelearnDatabase>().welearnDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            WelearnDatabase::class.java, "Welearn.db"
        ).fallbackToDestructiveMigration().build()
    }
}

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
    single { LocalDataSource(get()) }
//    factory { SharedPreference(get()) }
//    factory { AppExecutors() }
    single<IWelearnRepository> { WelearnRepository(get(), get())}
}