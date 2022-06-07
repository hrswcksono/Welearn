package com.tugasakhir.welearn

import android.app.Application
import com.tugasakhir.welearn.di.networkModule
import com.tugasakhir.welearn.di.repositoryModule
import com.tugasakhir.welearn.di.useCaseModule
import com.tugasakhir.welearn.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}