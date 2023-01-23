package com.tugasakhir.welearn.presentation.presenter.multiplayer

import com.tugasakhir.welearn.core.di.networkModule
import com.tugasakhir.welearn.core.di.repositoryModule
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
class PredictAngkaMultiPresenterTest : KoinTest {

    val predictAngkaMulti by inject<PredictAngkaMultiPresenter>()
    val login by inject<LoginPresenter>()

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
        login.loginUser("test", "12345").collectLatest { }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `predict_angka_number_one_multi_success`() = runBlocking{
        predictAngkaMulti.predictAngkaMulti(1, 1, 10, 10).collectLatest {
            assertEquals(it, "Benar")
        }
    }

    @Test
    fun `predict_angka_number_two_multi_success`() = runBlocking{
        predictAngkaMulti.predictAngkaMulti(1, 2, 10, 10).collectLatest {
            assertEquals(it, "Benar")
        }
    }

    @Test
    fun `predict_angka_number_three_multi_success`() = runBlocking{
        predictAngkaMulti.predictAngkaMulti(1, 3, 10, 10).collectLatest {
            assertEquals(it, "Benar")
        }
    }
}