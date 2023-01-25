package com.tugasakhir.welearn.presentation.presenter.singleplayer

import com.tugasakhir.welearn.core.di.networkModule
import com.tugasakhir.welearn.core.di.repositoryModule
import com.tugasakhir.welearn.di.presentationModule
import com.tugasakhir.welearn.di.useCaseModule
import com.tugasakhir.welearn.presentation.presenter.user.LoginPresenter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject


@ExperimentalCoroutinesApi
class ListSoalRandomPresenterTest : KoinTest {

    val soal by inject<ListSoalRandomPresenter>()
    val login by inject<LoginPresenter>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(listOf(
            networkModule,
            repositoryModule,
            useCaseModule,
            presentationModule
        ))
    }

    @Before
    fun before() = runBlocking {
        login.loginUser("Andi123", "12345").collectLatest {  }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `list_soal_huruf_success`() = runBlocking{
        soal.randomSoalSingle(1, 2).collectLatest {
            assertNotNull(it)
            assertEquals(10, it.size)
        }
    }

    @Test
    fun `list_soal_angka_success`() = runBlocking{
        soal.randomSoalSingle(2, 2).collectLatest {
            assertNotNull(it)
            assertEquals(10, it.size)
        }
    }


}