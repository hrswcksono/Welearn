package com.tugasakhir.welearn

import com.tugasakhir.welearn.data.di.*
import com.tugasakhir.welearn.di.presentationModule
import com.tugasakhir.welearn.di.useCaseModule
import org.junit.Test
import org.koin.test.check.checkModules

class CheckModule {

    @Test
    fun checkAllModules() = checkModules {
        modules(listOf(
            networkModule,
            useCaseModule,
            presentationModule,
            authModule,
            singleModule,
            multiModule
        ))
    }

}