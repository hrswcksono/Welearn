package com.tugasakhir.welearn.data.di

import android.content.SharedPreferences
import com.tugasakhir.welearn.utils.Constants.Companion.BASE_URL_API
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// jalankan lt --port 8000 --subdomain gfhdgdjsjcbsjcgdjsdjdjs
val networkModule = module {
    fun provideClient(): OkHttpClient {
         val clientBuilder =  OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)

//        clientBuilder.addInterceptor {chain ->
//            val newRequest = chain.request().newBuilder()
//                .addHeader( //I can't get token because there is no context here.
//                    "Authorization", "Bearer $preferences"
//                )
//                .build()
//            chain.proceed(newRequest)
//        }

        return clientBuilder.build()
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

//    single {
//        androidApplication().getSharedPreferences("PREFERENCES", android.content.Context.MODE_PRIVATE)
//    }

    single { provideClient() }
    single { provideRetrofit(get()) }
}



