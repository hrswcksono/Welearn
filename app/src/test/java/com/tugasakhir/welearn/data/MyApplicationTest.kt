package com.tugasakhir.welearn.data

import androidx.test.core.app.ApplicationProvider
import com.tugasakhir.welearn.core.data.WelearnRepository
import com.tugasakhir.welearn.core.di.networkModule
import com.tugasakhir.welearn.core.di.repositoryModule
import com.tugasakhir.welearn.di.useCaseModule
import com.tugasakhir.welearn.di.viewModelModule
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.test.inject
import org.koin.test.KoinTest

class MyApplicationTest : KoinTest{

    private val welearnRepository: WelearnRepository by inject()

    @Before
    fun before() {
        stopKoin()
        if (GlobalContext.getOrNull() == null) {
            loadKoinModules(
                listOf(
                    networkModule,
                    repositoryModule,
                    useCaseModule,
//                    viewModelModule
                )
            )
        }
        welearnRepository.loginUser("Andi123", "12345")
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun getDetailProfile() {
        val data = welearnRepository.detailUser()
//        assertEquals("test", data)
        assertNotNull(data)
    }

}