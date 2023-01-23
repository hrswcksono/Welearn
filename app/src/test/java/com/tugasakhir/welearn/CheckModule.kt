package com.tugasakhir.welearn

import com.tugasakhir.welearn.core.di.networkModule
import com.tugasakhir.welearn.core.di.repositoryModule
import com.tugasakhir.welearn.di.presentationModule
import com.tugasakhir.welearn.di.useCaseModule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.check.checkModules
import org.mockito.MockitoAnnotations

class CheckModule {

    // verify the Koin configuration
    @Test
    fun checkAllModules() = checkModules {
        modules(listOf(
            networkModule,
            repositoryModule,
            useCaseModule,
            presentationModule
        ))
    }
}