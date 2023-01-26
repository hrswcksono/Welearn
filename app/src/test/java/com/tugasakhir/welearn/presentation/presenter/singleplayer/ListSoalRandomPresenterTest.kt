package com.tugasakhir.welearn.presentation.presenter.singleplayer

import android.content.Context
import android.os.Build.VERSION_CODES.Q
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
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
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.annotation.Config


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
//@RunWith(AndroidJUnit4::class)
@Config(sdk = [Q])
class ListSoalRandomPresenterTest : KoinTest {

    val soal by inject<ListSoalRandomPresenter>()
    val login by inject<LoginPresenter>()
    lateinit var authToken: String
//
//    @Mock
//    private lateinit var mockContext: Context
//
////    val context = ApplicationProvider.getApplicationContext<Context>()

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
    fun `list_soal_huruf_success`() = runBlocking{
        soal.randomSoalSingle(1, 2, authToken).collectLatest {
            assertNotNull(it)
            assertEquals(10, it.size)
        }
    }

    @Test
    fun `list_soal_angka_success`() = runBlocking{
        soal.randomSoalSingle(2, 2, authToken).collectLatest {
            assertNotNull(it)
            assertEquals(10, it.size)
        }
    }


}