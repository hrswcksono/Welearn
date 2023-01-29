package com.tugasakhir.welearn.presentation.presenter.multiplayer

import com.tugasakhir.welearn.data.di.authModule
import com.tugasakhir.welearn.data.di.multiModule
import com.tugasakhir.welearn.data.di.networkModule
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
class JoinGamePresenterTest : KoinTest {

    val joinGame by inject<JoinGamePresenter>()
    val login by inject<LoginPresenter>()
    lateinit var authToken: String

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(
            listOf(
                networkModule,
                useCaseModule,
                presentationModule,
                authModule,
                multiModule
            )
        )
    }

    @Before
    fun before() = runBlocking {
        login.loginUser("admin", "admin123").collectLatest {
            authToken = it.token!!
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `join_game_success`() = runBlocking{
        joinGame.joinGame(6861, authToken).collectLatest {
            assertEquals(it.status, "Join Game Gagal")
        }
    }
}