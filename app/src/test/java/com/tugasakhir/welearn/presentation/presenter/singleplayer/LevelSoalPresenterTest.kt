package com.tugasakhir.welearn.presentation.presenter.singleplayer

import com.tugasakhir.welearn.data.di.networkModule
import com.tugasakhir.welearn.data.di.repositoryModule
import com.tugasakhir.welearn.di.presentationModule
import com.tugasakhir.welearn.di.useCaseModule
import com.tugasakhir.welearn.presentation.presenter.user.LoginPresenter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

@ExperimentalCoroutinesApi
class LevelSoalPresenterTest : KoinTest {

    val level by inject<LevelSoalPresenter>()
    val login by inject<LoginPresenter>()
    lateinit var authToken: String

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
        login.loginUser("Andi123", "12345").collectLatest {
            authToken = it.token
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `test_level_huruf_success`() = runBlocking{
        level.getLevelSoal(1, authToken).collectLatest {
            assertNotNull(it)
            assertEquals(4, it.size)
        }
    }

    @Test
    fun `test_level_angka_success`() = runBlocking{
        level.getLevelSoal(2, authToken).collectLatest {
            assertNotNull(it)
            assertEquals(5, it.size)
        }
    }
}