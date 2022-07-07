package com.tugasakhir.welearn.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tugasakhir.welearn.core.data.source.remote.RemoteDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import kotlinx.coroutines.flow.Flow
import com.tugasakhir.welearn.domain.entity.*

class WelearnRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(FakeRemoteDataSource::class.java)
    private val welearnRepository = FakeWelearnRepository(remote)

//    @Test
//    fun login() {
//        `when`<Flow<LoginEntity>>
//    }


}