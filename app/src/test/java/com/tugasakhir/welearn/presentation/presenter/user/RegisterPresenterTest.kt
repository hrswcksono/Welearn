package com.tugasakhir.welearn.presentation.presenter.user

import com.tugasakhir.welearn.core.di.networkModule
import com.tugasakhir.welearn.core.di.repositoryModule
import com.tugasakhir.welearn.di.presentationModule
import com.tugasakhir.welearn.di.useCaseModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
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
class RegisterPresenterTest : KoinTest {

    val register by inject<RegisterPresenter>()

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

    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `register_success`() = runBlocking{
        register.registerUser(
            "test",
            "12345",
            "test@gmail.com",
            "unit-test",
            "Laki-laki",
        ).collectLatest {
        }
    }
}