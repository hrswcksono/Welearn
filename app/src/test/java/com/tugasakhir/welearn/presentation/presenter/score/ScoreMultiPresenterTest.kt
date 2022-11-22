package com.tugasakhir.welearn.presentation.presenter.score

import com.tugasakhir.welearn.core.di.networkModule
import com.tugasakhir.welearn.core.di.repositoryModule
import com.tugasakhir.welearn.di.useCaseModule
import com.tugasakhir.welearn.di.viewModelModule
import com.tugasakhir.welearn.presentation.presenter.auth.LoginPresenter
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
class ScoreMultiPresenterTest  : KoinTest {

    val scoreMulti by inject<ScoreMultiPresenter>()
    val login by inject<LoginPresenter>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(listOf(
            networkModule,
            repositoryModule,
            useCaseModule,
            viewModelModule
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
    fun `show_list_score_multi`() = runBlocking{
        scoreMulti.scoreMulti(1).collectLatest {
            assertNotNull(it)
        }
    }

}