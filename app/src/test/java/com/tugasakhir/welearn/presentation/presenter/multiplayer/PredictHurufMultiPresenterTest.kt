package com.tugasakhir.welearn.presentation.presenter.multiplayer

import com.tugasakhir.welearn.core.di.networkModule
import com.tugasakhir.welearn.core.di.repositoryModule
import com.tugasakhir.welearn.di.presentationModule
import com.tugasakhir.welearn.di.useCaseModule
import com.tugasakhir.welearn.presentation.presenter.auth.LoginPresenter
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
class PredictHurufMultiPresenterTest : KoinTest {

    val predictHurufMulti by inject<PredictHurufMultiPresenter>()
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
        login.loginUser("qwerty123", "12345").collectLatest { }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `predict_huruf_number_one_multi_success`() = runBlocking{
        predictHurufMulti.predictHurufMulti(1, 95, 10, 10).collectLatest {
            assertEquals(it, "Benar")
        }
    }

    @Test
    fun `predict_huruf_number_two_multi_success`() = runBlocking{
        predictHurufMulti.predictHurufMulti(1, 97, 10, 20).collectLatest {
            assertEquals(it, "Benar")
        }
    }

    @Test
    fun `predict_huruf_number_three_multi_success`() = runBlocking{
        predictHurufMulti.predictHurufMulti(1, 98, 0, 30).collectLatest {
            assertEquals(it, "Salah")
        }
    }
}