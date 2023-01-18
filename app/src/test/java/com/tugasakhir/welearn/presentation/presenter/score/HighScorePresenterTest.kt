package com.tugasakhir.welearn.presentation.presenter.score

import com.tugasakhir.welearn.core.di.networkModule
import com.tugasakhir.welearn.core.di.repositoryModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class HighScorePresenterTest : KoinTest {

    val highScore by inject<HighScorePresenter>()
//    val login by inject<LoginPresenter>()
    val test by inject<UserRepository>()
//
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(
            listOf(
                networkModule,
                repositoryModule
            )
        )
    }
//
//    @get:Rule
//    val mockProvider = MockProviderRule.create { clazz ->
//        Mockito.mock(clazz.java)
//    }
//
    @Before
    fun before() = runBlocking {
        test.loginUser("aini", "aini")
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun addition_isCorrect() {
        assertNotNull(test.detailUser())
    }

//
//    @Test
//    fun `list_score_huruf_single_player_success`() = runBlocking {
//        highScore.highScore(1).collectLatest {
//            assertNotNull(it)
//        }
//    }
//
//    @Test
//    fun `list_score_angka_single_player_success`() = runBlocking {
//        highScore.highScore(2).collectLatest {
//            assertNotNull(it)
//        }
//    }

}