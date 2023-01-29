package com.tugasakhir.welearn.presentation.presenter.multiplayer

import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.data.di.*
import com.tugasakhir.welearn.di.presentationModule
import com.tugasakhir.welearn.di.useCaseModule
import com.tugasakhir.welearn.domain.entity.Room
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
class MakeRoomPresenterTest : KoinTest {

    val makeRoom by inject<MakeRoomPresenter>()
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
    fun `make_room_success`() = runBlocking{

        val test = Room(
            1,
            2
        )

        makeRoom.makeRoom(2,2, authToken).collectLatest {
            assertNotNull(it)
        }
    }

    @Test
    fun `random_id_multi_success`() = runBlocking{

        makeRoom.randomIDSoalMultiByLevel(1, 2, authToken).collectLatest {
            when(it){
                is Resource.Success->{
                    assertNotNull(it)
                }
                is Resource.Loading->{}
                is Resource.Error->{}
            }
        }
    }
}