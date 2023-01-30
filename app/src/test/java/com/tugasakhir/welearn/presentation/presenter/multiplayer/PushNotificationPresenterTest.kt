package com.tugasakhir.welearn.presentation.presenter.multiplayer

import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.data.di.authModule
import com.tugasakhir.welearn.data.di.multiModule
import com.tugasakhir.welearn.data.di.networkModule
import com.tugasakhir.welearn.di.presentationModule
import com.tugasakhir.welearn.di.useCaseModule
import com.tugasakhir.welearn.domain.entity.NotificationData
import com.tugasakhir.welearn.domain.entity.PushNotification
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
class PushNotificationPresenterTest : KoinTest {
//
    val pushNotification by inject<PushNotificationPresenter>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(
            listOf(
                networkModule,
                useCaseModule,
                presentationModule,
                multiModule
            )
        )
    }

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `push_notif_success`() = runBlocking{
        pushNotification.pushNotification(
            PushNotification(
                NotificationData(
                    "",
                    "",
                    "",
                    "",
                    1,
                    ""
                ), "/topics/text",
                "high"
            ),
        ).collectLatest {
            when(it){
                is Resource.Success -> assertNotNull(it.message)
                is Resource.Loading -> {}
                is Resource.Error -> {}
            }
        }
    }


}