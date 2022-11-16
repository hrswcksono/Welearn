package com.tugasakhir.welearn.data

import com.tugasakhir.welearn.core.di.networkModule
import com.tugasakhir.welearn.core.di.repositoryModule
import com.tugasakhir.welearn.di.useCaseModule
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
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