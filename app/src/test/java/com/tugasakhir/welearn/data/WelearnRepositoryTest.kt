package com.tugasakhir.welearn.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.mockito.Mockito.mock

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