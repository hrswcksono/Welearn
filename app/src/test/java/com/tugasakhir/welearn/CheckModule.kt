package com.tugasakhir.welearn

import com.tugasakhir.welearn.core.di.apiModule
import com.tugasakhir.welearn.core.di.dataSourceModule
import com.tugasakhir.welearn.core.di.networkModule
import com.tugasakhir.welearn.core.di.repositoryModule
import com.tugasakhir.welearn.di.presentationModule
import com.tugasakhir.welearn.di.useCaseModule
import org.junit.Test
import org.koin.test.check.checkModules

class CheckModule {

    @Test
    fun checkAllModules() = checkModules {
        modules(listOf(
            apiModule,
            dataSourceModule,
            networkModule,
            repositoryModule,
            useCaseModule,
            presentationModule
        ))
    }

}