package com.tugasakhir.welearn

import com.tugasakhir.welearn.core.di.networkModule
import com.tugasakhir.welearn.core.di.repositoryModule
import com.tugasakhir.welearn.core.utils.SessionManager
import com.tugasakhir.welearn.data.UserRepository
import com.tugasakhir.welearn.di.presentationModule
import com.tugasakhir.welearn.di.useCaseModule
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.check.checkModules
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CheckModule {

    // verify the Koin configuration
    @Test
    fun checkAllModules() = checkModules {
        MockitoAnnotations.initMocks(this)
        loadKoinModules(
            listOf(
                networkModule,
                repositoryModule,
                useCaseModule,
                presentationModule,
            )
        )
    }
}