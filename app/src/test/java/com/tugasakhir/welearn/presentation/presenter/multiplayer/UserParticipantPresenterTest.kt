package com.tugasakhir.welearn.presentation.presenter.multiplayer

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
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class UserParticipantPresenterTest : KoinTest {

    val userParticipant by inject<UserParticipantPresenter>()
    val login by inject<LoginPresenter>()
    lateinit var authToken: String

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(
            listOf(
                networkModule,
                repositoryModule,
                useCaseModule,
                presentationModule
            )
        )
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Before
    fun before() = runBlocking {
        login.loginUser("test", "12345").collectLatest {
            authToken = it.token
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `user_participant_successs`() = runBlocking{
        userParticipant.getListUserParticipant(1, authToken).collectLatest {
            assertEquals(it.size, 2)
        }
    }
}