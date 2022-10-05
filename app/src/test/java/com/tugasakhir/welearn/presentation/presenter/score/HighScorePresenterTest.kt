package com.tugasakhir.welearn.presentation.presenter.score

import com.tugasakhir.welearn.core.di.networkModule
import com.tugasakhir.welearn.core.di.repositoryModule
import com.tugasakhir.welearn.di.useCaseModule
import com.tugasakhir.welearn.di.viewModelModule
import com.tugasakhir.welearn.presentation.presenter.auth.LoginPresenter
import com.tugasakhir.welearn.presentation.presenter.singleplayer.ListSoalRandomPresenter
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
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class HighScorePresenterTest : KoinTest {

    val highScore by inject<HighScorePresenter>()
    val login by inject<LoginPresenter>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(
            listOf(
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        )
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Before
    fun before() = runBlocking {
        login.loginUser("Andi123", "12345").collectLatest { }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `list_score_huruf_single_player`() = runBlocking {
        highScore.highScore(1).collectLatest {
            assertNotNull(it)
        }
    }

    @Test
    fun `list_score_angka_single_player`() = runBlocking {
        highScore.highScore(2).collectLatest {
            assertNotNull(it)
        }
    }

}