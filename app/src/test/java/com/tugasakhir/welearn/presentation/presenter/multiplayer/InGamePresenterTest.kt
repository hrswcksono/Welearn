package com.tugasakhir.welearn.presentation.presenter.multiplayer

import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.data.di.authModule
import com.tugasakhir.welearn.data.di.multiModule
import com.tugasakhir.welearn.data.di.networkModule
import com.tugasakhir.welearn.data.di.singleModule
import com.tugasakhir.welearn.di.presentationModule
import com.tugasakhir.welearn.di.useCaseModule
import com.tugasakhir.welearn.presentation.presenter.user.LoginPresenter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import okhttp3.Response
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
class InGamePresenterTest : KoinTest {

    val login by inject<LoginPresenter>()
    val inGamePresenter by inject<InGamePresenter>()
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
    fun setUp() = runBlocking {
        login.loginUser("admin", "admin123").collectLatest {
            authToken = it.token!!
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `get_user_participant_successs`() = runBlocking{
        inGamePresenter.getListUserParticipant(2, authToken).collectLatest {
            assertNotNull(it)
        }
    }

    @Test
    fun `get_soal_success`() = runBlocking{
        inGamePresenter.getSoalByID(1, authToken).collectLatest {
            assertNotNull(it)
        }
    }
    @Test
    fun `save_predict_huruf_multi_success`() = runBlocking{
        inGamePresenter.savePredictHurufMulti(1, 95, 10, 10, authToken).collectLatest {
            when(it){
                is Resource.Success -> assertEquals(it.data!!.status, "Berhasil Submit Nilai")
                is Resource.Loading -> {}
                is Resource.Error -> {}
            }
        }
    }

    @Test
    fun `save_predict_huruf_angka_success`() = runBlocking{
        inGamePresenter.savePredictAngkaMulti(1, 1, 10, 10, authToken).collectLatest {
            when(it){
                is Resource.Success -> assertEquals(it.data!!.status, "Berhasil Submit Nilai")
                is Resource.Loading -> {}
                is Resource.Error -> {}
            }
        }
    }

    @Test
    fun `end_game_success`() = runBlocking{
        inGamePresenter.gameAlreadyEnd("1", authToken).collectLatest {
            when(it){
                is Resource.Success -> assertNotNull(it.data)
                is Resource.Loading -> {}
                is Resource.Error -> {}
            }
        }
    }

//    fun `forced`
}