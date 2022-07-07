package com.tugasakhir.welearn

import com.tugasakhir.welearn.core.di.networkModule
import com.tugasakhir.welearn.core.di.repositoryModule
import com.tugasakhir.welearn.di.useCaseModule
import com.tugasakhir.welearn.di.viewModelModule
import org.junit.Rule
import org.junit.Test
import org.koin.test.check.checkModules
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito

class CheckModule {

    // Declare Mock with Mockito
    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    // verify the Koin configuration
    @Test
    fun checkAllModules() = checkModules {
        modules(listOf(
            networkModule,
            repositoryModule,
            useCaseModule,
            viewModelModule
        ))
    }
}