package com.tugasakhir.welearn

import android.app.Application
import com.tugasakhir.welearn.core.di.apiModule
import com.tugasakhir.welearn.core.di.dataSourceModule
import com.tugasakhir.welearn.core.di.networkModule
import com.tugasakhir.welearn.core.di.repositoryModule
import com.tugasakhir.welearn.di.presentationModule
import com.tugasakhir.welearn.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    apiModule,
                    dataSourceModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    presentationModule
                )
            )
        }
    }
}